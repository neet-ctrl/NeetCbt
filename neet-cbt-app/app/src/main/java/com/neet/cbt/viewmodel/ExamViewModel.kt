package com.neet.cbt.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.neet.cbt.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

// ─── Navigation Screens ──────────────────────────────────────────────────────

sealed class Screen {
    object FileSelect : Screen()
    object Login : Screen()
    object Instructions : Screen()
    object Exam : Screen()
    object Summary : Screen()
    object Result : Screen()
}

// ─── ViewModel ────────────────────────────────────────────────────────────────

class ExamViewModel(application: Application) : AndroidViewModel(application) {

    // ── Navigation ──────────────────────────────────────────────────────────

    private val _screen = MutableStateFlow<Screen>(Screen.Login)
    val screen: StateFlow<Screen> = _screen.asStateFlow()

    fun navigate(screen: Screen) { _screen.value = screen }

    // ── Exam Data ────────────────────────────────────────────────────────────

    private val _exam = MutableStateFlow(buildExam())
    val exam: StateFlow<Exam> = _exam.asStateFlow()

    // All questions flattened for easy lookup
    private val allQuestions: List<Question>
        get() = _exam.value.sections.flatMap { it.questions }

    // ── Section & Question Navigation ────────────────────────────────────────

    private val _currentSectionIndex = MutableStateFlow(0)
    val currentSectionIndex: StateFlow<Int> = _currentSectionIndex.asStateFlow()

    private val _currentQuestionIndexInSection = MutableStateFlow(0)
    val currentQuestionIndexInSection: StateFlow<Int> = _currentQuestionIndexInSection.asStateFlow()

    val currentSection: Section
        get() = _exam.value.sections[_currentSectionIndex.value]

    val currentQuestion: Question
        get() = currentSection.questions[_currentQuestionIndexInSection.value]

    // ── Answer & Status Maps ──────────────────────────────────────────────────

    // questionId → selected option index (0-based); null means no selection
    private val _answers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val answers: StateFlow<Map<Int, Int>> = _answers.asStateFlow()

    // questionId → QuestionStatus
    private val _statuses = MutableStateFlow<Map<Int, QuestionStatus>>(emptyMap())
    val statuses: StateFlow<Map<Int, QuestionStatus>> = _statuses.asStateFlow()

    // Temporary in-flight option chosen but NOT yet saved
    private val _tempSelection = MutableStateFlow<Int?>(null)
    val tempSelection: StateFlow<Int?> = _tempSelection.asStateFlow()

    // ── Timer ────────────────────────────────────────────────────────────────

    private val totalSeconds = _exam.value.totalTimeMinutes * 60L
    private val _remainingSeconds = MutableStateFlow(totalSeconds)
    val remainingSeconds: StateFlow<Long> = _remainingSeconds.asStateFlow()

    private var timerJob: Job? = null

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_remainingSeconds.value > 0) {
                delay(1000L)
                _remainingSeconds.value -= 1
            }
            // Auto-submit on timer expiry
            navigate(Screen.Summary)
        }
    }

    fun stopTimer() { timerJob?.cancel() }

    // ── Instructions checkbox ─────────────────────────────────────────────────

    private val _instructionsAccepted = MutableStateFlow(false)
    val instructionsAccepted: StateFlow<Boolean> = _instructionsAccepted.asStateFlow()

    fun setInstructionsAccepted(v: Boolean) { _instructionsAccepted.value = v }

    // ── Palette Visibility ────────────────────────────────────────────────────

    private val _paletteVisible = MutableStateFlow(true)
    val paletteVisible: StateFlow<Boolean> = _paletteVisible.asStateFlow()

    fun togglePalette() { _paletteVisible.value = !_paletteVisible.value }

    // ── PDF Bitmap Cache ──────────────────────────────────────────────────────

    private val bitmapCache = mutableMapOf<Int, Bitmap>()
    private var pdfFile: File? = null

    private val _currentBitmap = MutableStateFlow<Bitmap?>(null)
    val currentBitmap: StateFlow<Bitmap?> = _currentBitmap.asStateFlow()

    fun setPdfFile(file: File) { pdfFile = file }

    fun loadBitmapForCurrentQuestion() {
        val q = currentQuestion
        if (!q.hasImage) { _currentBitmap.value = null; return }
        val cached = bitmapCache[q.id]
        if (cached != null) { _currentBitmap.value = cached; return }

        viewModelScope.launch(Dispatchers.IO) {
            val pdf = pdfFile ?: return@launch
            val bitmap = renderPage(pdf, q.id - 1)
            bitmap?.let { bitmapCache[q.id] = it }
            _currentBitmap.value = bitmap
        }
    }

    private fun renderPage(pdfFile: File, pageIndex: Int): Bitmap? {
        return try {
            val fd = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            PdfRenderer(fd).use { renderer ->
                val safeIndex = pageIndex.coerceIn(0, renderer.pageCount - 1)
                renderer.openPage(safeIndex).use { page ->
                    val scale = 2f
                    val w = (page.width * scale).toInt()
                    val h = (page.height * scale).toInt()
                    val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
                    page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    bmp
                }
            }
        } catch (e: Exception) {
            Log.e("ExamViewModel", "PDF render error: ${e.message}")
            null
        }
    }

    // ── Option Selection (temp, not saved) ──────────────────────────────────

    fun selectOption(optionIndex: Int) {
        val current = _tempSelection.value
        // Toggle off if same option tapped twice
        _tempSelection.value = if (current == optionIndex) null else optionIndex
    }

    /** Re-load existing answer into temp selection when navigating to a question */
    private fun syncTempSelection() {
        _tempSelection.value = _answers.value[currentQuestion.id]
    }

    // ── Navigation Actions ────────────────────────────────────────────────────

    fun navigateToQuestion(sectionIndex: Int, indexInSection: Int) {
        // Mark current question's status if still NOT_VISITED → NOT_ANSWERED
        val q = currentQuestion
        val currentStatus = _statuses.value[q.id] ?: QuestionStatus.NOT_VISITED
        if (currentStatus == QuestionStatus.NOT_VISITED) {
            updateStatus(q.id, QuestionStatus.NOT_ANSWERED)
        }

        _currentSectionIndex.value = sectionIndex
        _currentQuestionIndexInSection.value = indexInSection
        syncTempSelection()
        loadBitmapForCurrentQuestion()
    }

    fun navigateToQuestionById(questionId: Int) {
        val exam = _exam.value
        for ((si, section) in exam.sections.withIndex()) {
            val qi = section.questions.indexOfFirst { it.id == questionId }
            if (qi >= 0) {
                navigateToQuestion(si, qi)
                return
            }
        }
    }

    fun goNext() {
        val section = currentSection
        val nextIdx = _currentQuestionIndexInSection.value + 1
        if (nextIdx < section.questions.size) {
            navigateToQuestion(_currentSectionIndex.value, nextIdx)
        } else {
            // Move to next section
            val nextSi = _currentSectionIndex.value + 1
            if (nextSi < _exam.value.sections.size) {
                navigateToQuestion(nextSi, 0)
            }
        }
    }

    fun goBack() {
        val prevIdx = _currentQuestionIndexInSection.value - 1
        if (prevIdx >= 0) {
            navigateToQuestion(_currentSectionIndex.value, prevIdx)
        } else {
            val prevSi = _currentSectionIndex.value - 1
            if (prevSi >= 0) {
                val lastQ = _exam.value.sections[prevSi].questions.size - 1
                navigateToQuestion(prevSi, lastQ)
            }
        }
    }

    // ── Button Actions ────────────────────────────────────────────────────────

    /** SAVE & NEXT: save answer → ANSWERED → move next */
    fun saveAndNext() {
        val selected = _tempSelection.value
        val qId = currentQuestion.id
        if (selected != null) {
            saveAnswer(qId, selected)
            updateStatus(qId, QuestionStatus.ANSWERED)
        } else {
            updateStatus(qId, QuestionStatus.NOT_ANSWERED)
        }
        goNext()
    }

    /** CLEAR: remove answer → NOT_ANSWERED */
    fun clearAnswer() {
        val qId = currentQuestion.id
        _answers.value = _answers.value - qId
        _tempSelection.value = null
        updateStatus(qId, QuestionStatus.NOT_ANSWERED)
    }

    /** SAVE & MARK FOR REVIEW: save + ANSWERED_AND_MARKED, stay */
    fun saveAndMarkForReview() {
        val selected = _tempSelection.value
        val qId = currentQuestion.id
        if (selected != null) {
            saveAnswer(qId, selected)
            updateStatus(qId, QuestionStatus.ANSWERED_AND_MARKED)
        } else {
            updateStatus(qId, QuestionStatus.MARKED_FOR_REVIEW)
        }
    }

    /** MARK FOR REVIEW & NEXT: mark → move next */
    fun markForReviewAndNext() {
        val selected = _tempSelection.value
        val qId = currentQuestion.id
        if (selected != null) {
            saveAnswer(qId, selected)
            updateStatus(qId, QuestionStatus.ANSWERED_AND_MARKED)
        } else {
            updateStatus(qId, QuestionStatus.MARKED_FOR_REVIEW)
        }
        goNext()
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun saveAnswer(questionId: Int, optionIndex: Int) {
        _answers.value = _answers.value + (questionId to optionIndex)
    }

    private fun updateStatus(questionId: Int, status: QuestionStatus) {
        _statuses.value = _statuses.value + (questionId to status)
    }

    // ── Summary Counts ────────────────────────────────────────────────────────

    data class SummaryCount(
        val total: Int,
        val answered: Int,
        val notAnswered: Int,
        val markedForReview: Int,
        val answeredAndMarked: Int,
        val notVisited: Int
    )

    fun getSummaryCount(): SummaryCount {
        val all = allQuestions
        val statuses = _statuses.value
        var answered = 0; var notAnswered = 0; var marked = 0; var answeredMarked = 0; var notVisited = 0
        for (q in all) {
            when (statuses[q.id] ?: QuestionStatus.NOT_VISITED) {
                QuestionStatus.ANSWERED -> answered++
                QuestionStatus.NOT_ANSWERED -> notAnswered++
                QuestionStatus.MARKED_FOR_REVIEW -> marked++
                QuestionStatus.ANSWERED_AND_MARKED -> answeredMarked++
                QuestionStatus.NOT_VISITED -> notVisited++
            }
        }
        return SummaryCount(all.size, answered, notAnswered, marked, answeredMarked, notVisited)
    }

    // ── Score ─────────────────────────────────────────────────────────────────

    fun getScoreResult(): ScoreResult = calculateScore(_exam.value, _answers.value)

    // ── Init ──────────────────────────────────────────────────────────────────

    init {
        // Mark question 1 as NOT_VISITED initially (will switch to NOT_ANSWERED on open)
        loadBitmapForCurrentQuestion()
    }

    override fun onCleared() {
        super.onCleared()
        bitmapCache.values.forEach { it.recycle() }
        bitmapCache.clear()
        timerJob?.cancel()
    }
}
