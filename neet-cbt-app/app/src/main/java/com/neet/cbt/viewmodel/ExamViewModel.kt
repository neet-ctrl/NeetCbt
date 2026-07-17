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
     * Exact question-ID → 0-based PDF page index for every hasImage=true question.
     * Derived by running pdftotext on ReNeetCode70.pdf and locating each question's
     * "Page N of 61" header, then computing 0-indexed page = N-1.
     * ALL 36 image questions are mapped — none will show an infinite spinner.
     */
    private val questionPageMap: Map<Int, Int> = mapOf(
        // ── Physics (Q1-Q45) ─────────────────────────────────────────────
        1  to 0,   // Q1:  PDF page 1  – photon/electron momentum diagram
        3  to 0,   // Q3:  PDF page 1  – disc rotation angular momentum figure
        7  to 2,   // Q7:  PDF page 3  – photocurrent vs stopping-potential graphs
        8  to 3,   // Q8:  PDF page 4  – terminal velocity graph options
        9  to 3,   // Q9:  PDF page 4  – Zener diode circuit figure
        10 to 4,   // Q10: PDF page 5  – escape speed planet figure
        13 to 6,   // Q13: PDF page 7  – three capacitors P Q S circuit
        14 to 7,   // Q14: PDF page 8  – conducting loop x-y plane
        15 to 8,   // Q15: PDF page 9  – adiabatic expansion P-V graph
        20 to 10,  // Q20: PDF page 11 – circular loop current figure
        21 to 11,  // Q21: PDF page 12 – pendulum bob B figure
        26 to 14,  // Q26: PDF page 15 – banked circular track figure
        28 to 15,  // Q28: PDF page 16 – p-n junction diode circuit
        29 to 15,  // Q29: PDF page 16 – lens combination L1 L2 figure
        30 to 16,  // Q30: PDF page 17 – solid sphere A + sphere B
        31 to 16,  // Q31: PDF page 17 – hydrogen atom orbital figure
        42 to 20,  // Q42: PDF page 21 – Geiger-Marsden N(θ) plot
        43 to 20,  // Q43: PDF page 21 – monatomic gas cyclic process
        44 to 21,  // Q44: PDF page 22 – two parallel conducting wires figure
        // ── Chemistry (Q46-Q90) ──────────────────────────────────────────
        47 to 22,  // Q47: PDF page 23 – tetraammineaquachloridocobalt complex
        50 to 23,  // Q50: PDF page 24 – crystal/unit cell figure
        52 to 24,  // Q52: PDF page 25 – electrochemistry/reaction diagram
        53 to 24,  // Q53: PDF page 25 – organic structure figure
        59 to 26,  // Q59: PDF page 27 – organic reaction mechanism
        61 to 27,  // Q61: PDF page 28 – organic compound structure
        65 to 28,  // Q65: PDF page 29 – reaction pathway / product figure
        66 to 29,  // Q66: PDF page 30 – NMR / spectra figure
        // ── Botany / Zoology (Q91-Q180) ──────────────────────────────────
        81  to 33, // Q81: PDF page 34 – transition metal complex diagram
        89  to 35, // Q89: PDF page 36 – organic compound structure
        90  to 36, // Q90: PDF page 37 – biomolecule structure
        91  to 36, // Q91: PDF page 37 – plant cell / tissue figure
        97  to 38, // Q97: PDF page 39 – anatomy / morphology diagram
        113 to 42, // Q113: PDF page 43 – biology figure
        119 to 44, // Q119: PDF page 45 – reproductive biology figure
        123 to 45, // Q123: PDF page 46 – cell biology diagram
        125 to 45  // Q125: PDF page 46 – genetics / evolution figure
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
