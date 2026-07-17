package com.neet.cbt.data

// ─── Question Status ────────────────────────────────────────────────────────

enum class QuestionStatus {
    NOT_VISITED,         // Grey  – never opened
    NOT_ANSWERED,        // Red   – opened but no option chosen
    ANSWERED,            // Green – option saved
    MARKED_FOR_REVIEW,   // Purple – no answer, marked for review
    ANSWERED_AND_MARKED  // Purple+Green – saved AND marked for review
}

// ─── Data Classes ────────────────────────────────────────────────────────────

/**
 * A single MCQ question.
 * @param id          1-based question number (global, across all sections)
 * @param text        Full question stem
 * @param options     Exactly 4 options (index 0 = A/1, 1 = B/2, 2 = C/3, 3 = D/4)
 * @param correctOption 0-indexed correct answer; -1 = unknown/dropped; -2 = bonus (all correct)
 * @param hasImage    True if the original question paper has a diagram on this question's page
 */
data class Question(
    val id: Int,
    val text: String,
    val options: List<String>,
    val correctOption: Int = -1,
    val hasImage: Boolean = false
)

/**
 * A section of the exam (Physics / Chemistry / Botany / Zoology).
 */
data class Section(
    val name: String,
    val questions: List<Question>
)

/**
 * The full exam.
 */
data class Exam(
    val name: String = "NEET (UG) Re-Exam 2026",
    val code: String = "70",
    val totalTimeMinutes: Int = 195, // 3 hrs 15 min
    val sections: List<Section>
)

// ─── Scoring ──────────────────────────────────────────────────────────────────

const val MARKS_CORRECT = 4
const val MARKS_WRONG = -1
const val MARKS_NOT_ATTEMPTED = 0

fun calculateScore(
    exam: Exam,
    answers: Map<Int, Int> // questionId -> selected option index (0-based)
): ScoreResult {
    var correct = 0
    var incorrect = 0
    var notAttempted = 0
    var score = 0
    val allQuestions = exam.sections.flatMap { it.questions }

    for (q in allQuestions) {
        val selected = answers[q.id]
        when {
            selected == null -> {
                notAttempted++
                // Dropped questions (correctOption == -2) give full marks automatically
                if (q.correctOption == -2) score += MARKS_CORRECT
            }
            q.correctOption == -2 -> {
                // Bonus – full marks regardless
                correct++
                score += MARKS_CORRECT
            }
            q.correctOption == -1 -> {
                // Unknown – practice mode
                notAttempted++
            }
            selected == q.correctOption -> {
                correct++
                score += MARKS_CORRECT
            }
            else -> {
                incorrect++
                score += MARKS_WRONG
            }
        }
    }
    return ScoreResult(
        total = allQuestions.size,
        attempted = correct + incorrect,
        correct = correct,
        incorrect = incorrect,
        notAttempted = notAttempted,
        score = score
    )
}

data class ScoreResult(
    val total: Int,
    val attempted: Int,
    val correct: Int,
    val incorrect: Int,
    val notAttempted: Int,
    val score: Int
)
