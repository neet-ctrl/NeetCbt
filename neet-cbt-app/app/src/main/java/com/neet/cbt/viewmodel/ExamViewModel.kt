package com.neet.cbt.viewmodel

import android.app.Application
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
import java.io.FileOutputStream

// ─── Navigation Screens ──────────────────────────────────────────────────────

sealed class Screen {
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

    private val _answers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val answers: StateFlow<Map<Int, Int>> = _answers.asStateFlow()

    private val _statuses = MutableStateFlow<Map<Int, QuestionStatus>>(emptyMap())
    val statuses: StateFlow<Map<Int, QuestionStatus>> = _statuses.asStateFlow()

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

    /**
     * Exact question-ID → 0-based PDF page index mapping derived from the official
     * NEET Re-Exam 2026 Code 70 question paper (ReNeetCode70.pdf).
     * Only questions with hasImage = true need an entry; all others are ignored.
     */
    private val questionPageMap: Map<Int, Int> = mapOf(
        // ── Physics (Q1-Q45) ──────────────────────────────────────────────
        1  to 0,   // Q1:  momentum ratio diagram
        2  to 0,   // Q2:  pipe cross-section flow figure
        6  to 2,   // Q6:  circuit +α/-α diagram
        7  to 2,   // Q7:  photocurrent stopping-potential graphs
        8  to 3,   // Q8:  terminal velocity graphs
        9  to 3,   // Q9:  Zener diode circuit
        11 to 5,   // Q11: LCR AC circuit
        12 to 5,   // Q12: inductors P and Q configuration
        13 to 6,   // Q13: three capacitors P Q S circuit
        14 to 7,   // Q14: conducting loop x-y plane
        18 to 9,   // Q18: polyatomic gas graph options
        19 to 10,  // Q19: unit charge tube figure
        20 to 10,  // Q20: circular loop current figure
        25 to 13,  // Q25: charged insulating sphere graph
        26 to 13,  // Q26: (continuation, same page)
        27 to 14,  // Q27: frictionless circular wire particles
        28 to 14,  // Q28: (same page as Q27)
        29 to 15,  // Q29: lens combination L1 L2 figure
        30 to 16,  // Q30: solid sphere A + sphere B figure
        41 to 19,  // Q41: conducting sphere with cavity & points
        42 to 20,  // Q42: Geiger-Marsden N(θ) plot
        43 to 20,  // Q43: monatomic gas cyclic process diagram
        // ── Chemistry (Q46-Q90) ───────────────────────────────────────────
        46 to 22,  // Q46: chemical reaction diagram
        48 to 22,  // Q48: octahedral complex geometry
        49 to 22,  // Q49: molecule structure diagram
        51 to 23,  // Q51: titration/reaction figure
        52 to 24,  // Q52: crystal structure diagram
        58 to 26,  // Q58: organic reaction mechanism
        60 to 26,  // Q60: reaction product structure
        63 to 28,  // Q63: NMR / spectra figure
        64 to 28,  // Q64: organic compound structure
        65 to 28,  // Q65: reaction pathway diagram
        80 to 32,  // Q80: transition metal complex diagram
        87 to 35,  // Q87: polymer/monomer structure
        88 to 35,  // Q88: organic named reaction figure
        89 to 35,  // Q89: compound structure diagram
        90 to 36,  // Q90: biomolecule structure
        // ── Botany (Q91-Q135) ─────────────────────────────────────────────
        95  to 37, // Q95: plant tissue figure
        96  to 37, // Q96: anatomy diagram
        112 to 41, // Q112: photosynthesis electron transport figure
        118 to 43, // Q118: plant reproduction diagram
        121 to 44, // Q121: flower part diagram
        122 to 44, // Q122: cell cycle figure
        123 to 44, // Q123: mitosis/meiosis diagram
        124 to 45, // Q124: chromosome structure
        127 to 46  // Q127: plant hormone pathway
    )

    private val bitmapCache = mutableMapOf<Int, Bitmap>()
    private var pdfFile: File? = null

    private val _currentBitmap = MutableStateFlow<Bitmap?>(null)
    val currentBitmap: StateFlow<Bitmap?> = _currentBitmap.asStateFlow()

    /** Called from MainActivity once the PDF has been copied to the cache dir */
    fun setPdfFile(file: File) {
        pdfFile = file
        // Immediately try loading for the current question if it needs an image
        loadBitmapForCurrentQuestion()
    }

    fun loadBitmapForCurrentQuestion() {
        val q = currentQuestion
        if (!q.hasImage) {
            _currentBitmap.value = null
            return
        }
        val cached = bitmapCache[q.id]
        if (cached != null) {
            _currentBitmap.value = cached
            return
        }
        val pageIndex = questionPageMap[q.id] ?: return

        viewModelScope.launch(Dispatchers.IO) {
            val pdf = pdfFile ?: return@launch
            val bitmap = renderPage(pdf, pageIndex)
            withContext(Dispatchers.Main) {
                bitmap?.let { bitmapCache[q.id] = it }
                _currentBitmap.value = bitmap
            }
        }
    }

    private fun renderPage(pdfFile: File, pageIndex: Int): Bitmap? {
        return try {
            val fd = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            PdfRenderer(fd).use { renderer ->
                val safeIndex = pageIndex.coerceIn(0, renderer.pageCount - 1)
                renderer.openPage(safeIndex).use { page ->
                    // Render at 2× for crisp display
                    val scale = 2f
                    val w = (page.width * scale).toInt()
                    val h = (page.height * scale).toInt()
                    val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
                    page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    bmp
                }
            }
        } catch (e: Exception) {
            Log.e("ExamViewModel", "PDF render error page $pageIndex: ${e.message}")
            null
        }
    }

    // ── Option Selection ───────────────────────────────────────────────────

    fun selectOption(optionIndex: Int) {
        val current = _tempSelection.value
        _tempSelection.value = if (current == optionIndex) null else optionIndex
    }

    private fun syncTempSelection() {
        _tempSelection.value = _answers.value[currentQuestion.id]
    }

    // ── Navigation Actions ────────────────────────────────────────────────────

    fun navigateToQuestion(sectionIndex: Int, indexInSection: Int) {
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

    /** CLEAR: remove answer → NOT_ANSWERED, stay on question */
    fun clearAnswer() {
        val qId = currentQuestion.id
        _answers.value = _answers.value - qId
        _tempSelection.value = null
        updateStatus(qId, QuestionStatus.NOT_ANSWERED)
    }

    /** SAVE & MARK FOR REVIEW: save + mark, stay on question */
    fun saveAndMarkForReview() {
        val selected = _tempSelection.value
        val qId = currentQuestion.id
        if (selected != null) {
            saveAnswer(qId, selected)
            updateStatus(qId, QuestionStatus.ANSWERED_AND_MARKED)
        } else {
            updateStatus(qId, QuestionStatus.MARKED_FOR_REVIEW)
        }
        // Intentionally stays on current question (NTA behavior)
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
                QuestionStatus.ANSWERED            -> answered++
                QuestionStatus.NOT_ANSWERED        -> notAnswered++
                QuestionStatus.MARKED_FOR_REVIEW   -> marked++
                QuestionStatus.ANSWERED_AND_MARKED -> answeredMarked++
                QuestionStatus.NOT_VISITED         -> notVisited++
            }
        }
        return SummaryCount(all.size, answered, notAnswered, marked, answeredMarked, notVisited)
    }

    // ── Score ─────────────────────────────────────────────────────────────────

    fun getScoreResult(): ScoreResult = calculateScore(_exam.value, _answers.value)

    // ── Init ──────────────────────────────────────────────────────────────────

    init {
        // Nothing to do here; PDF is loaded async by MainActivity
    }

    override fun onCleared() {
        super.onCleared()
        bitmapCache.values.forEach { it.recycle() }
        bitmapCache.clear()
        timerJob?.cancel()
    }
}
