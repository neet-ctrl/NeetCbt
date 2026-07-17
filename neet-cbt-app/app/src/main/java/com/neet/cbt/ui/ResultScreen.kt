package com.neet.cbt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.data.QuestionStatus
import com.neet.cbt.ui.theme.*
import com.neet.cbt.viewmodel.ExamViewModel

@Composable
fun ResultScreen(vm: ExamViewModel) {
    val exam    by vm.exam.collectAsState()
    val answers by vm.answers.collectAsState()
    val statuses by vm.statuses.collectAsState()
    val score   = remember { vm.getScoreResult() }
    var searchText by remember { mutableStateOf("") }

    val allQuestions = exam.sections.flatMap { it.questions }
    val filteredQuestions = if (searchText.isBlank()) allQuestions
    else allQuestions.filter { it.id.toString().contains(searchText.trim()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NTALightGrey)
    ) {
        NTAHeader()

        // ── Success Banner ────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(NTAGreen)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text       = "✓  Thank you — Submitted Successfully!",
                color      = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize   = 14.sp
            )
        }

        // ── Score Cards ───────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ScoreCard("Total Qs",  score.total.toString(),     Color(0xFF2563EB), Modifier.weight(1f))
            ScoreCard("Attempted", score.attempted.toString(), Color(0xFF7C3AED), Modifier.weight(1f))
            ScoreCard("Correct",   score.correct.toString(),   NTAGreen,          Modifier.weight(1f))
            ScoreCard("Incorrect", score.incorrect.toString(), NTARed,            Modifier.weight(1f))
            ScoreCard("Score",     score.score.toString(),     NTABlue,           Modifier.weight(1f))
            ScoreCard("Max Marks", "720",                      Color(0xFF6B7280), Modifier.weight(1f))
        }

        // ── Score Percentage Bar ──────────────────────────────────────────────
        val percentage = if (score.total > 0)
            (score.score.toFloat() / 720f * 100f).coerceIn(0f, 100f) else 0f

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape  = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Score: ${score.score} / 720",
                        fontWeight = FontWeight.Bold,
                        fontSize   = 13.sp,
                        color      = NTABlue
                    )
                    Text(
                        "%.1f%%".format(percentage),
                        fontWeight = FontWeight.Bold,
                        fontSize   = 13.sp,
                        color      = NTAGreen
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress    = { percentage / 100f },
                    modifier    = Modifier.fillMaxWidth().height(8.dp),
                    color       = when {
                        percentage >= 60 -> NTAGreen
                        percentage >= 40 -> NTAOrange
                        else             -> NTARed
                    },
                    trackColor  = NTALightGrey
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Question-wise Analysis Table ──────────────────────────────────────
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape  = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Search row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Question-wise Analysis",
                        fontWeight = FontWeight.Bold,
                        fontSize   = 13.sp,
                        color      = NTABlue,
                        modifier   = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value           = searchText,
                        onValueChange   = { searchText = it },
                        placeholder     = { Text("Search Q#", fontSize = 11.sp) },
                        singleLine      = true,
                        modifier        = Modifier.width(130.dp).height(40.dp),
                        shape           = RoundedCornerShape(6.dp),
                        textStyle       = LocalTextStyle.current.copy(fontSize = 12.sp),
                        colors          = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = NTALightBlue,
                            unfocusedBorderColor = NTABorder
                        )
                    )
                }

                // Table header
                TableHeader()

                // Question rows
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredQuestions) { q ->
                        val selectedIdx = answers[q.id]
                        val isAnswered  = selectedIdx != null
                        // correctOption == -2 means NTA dropped the question (bonus to all)
                        val isDropped   = q.correctOption == -2
                        val isCorrect   = !isDropped && isAnswered &&
                                q.correctOption >= 0 && selectedIdx == q.correctOption

                        TableRow(
                            questionId     = q.id,
                            selectedOption = selectedIdx?.let { listOf("A","B","C","D")[it] } ?: "--",
                            status         = when {
                                isDropped    -> "Dropped (+4)"
                                !isAnswered  -> "Not Attempted"
                                isCorrect    -> "Correct ✓"
                                else         -> "Incorrect ✗"
                            },
                            statusColor    = when {
                                isDropped    -> Color(0xFF7C3AED)
                                !isAnswered  -> NTADarkGrey
                                isCorrect    -> NTAGreen
                                else         -> NTARed
                            },
                            correctOption  = when {
                                isDropped           -> "Dropped"
                                q.correctOption >= 0 -> listOf("A","B","C","D")[q.correctOption]
                                else                 -> "N/A"
                            },
                            marks          = when {
                                isDropped    -> "+4"
                                !isAnswered  -> "0"
                                isCorrect    -> "+4"
                                else         -> "-1"
                            },
                            marksColor     = when {
                                isDropped    -> Color(0xFF7C3AED)
                                !isAnswered  -> NTADarkGrey
                                isCorrect    -> NTAGreen
                                else         -> NTARed
                            }
                        )
                        HorizontalDivider(color = NTABorder, thickness = 0.5.dp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Footer ───────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(NTABlue)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "© All Rights Reserved - National Testing Agency",
                color    = Color.White,
                fontSize = 11.sp
            )
        }
    }
}

// ─── Private Composables ──────────────────────────────────────────────────────

@Composable
private fun ScoreCard(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors   = CardDefaults.cardColors(containerColor = color),
        shape    = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier              = Modifier.padding(10.dp),
            horizontalAlignment   = Alignment.CenterHorizontally,
            verticalArrangement   = Arrangement.Center
        ) {
            Text(
                value,
                color      = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize   = 22.sp,
                textAlign  = TextAlign.Center
            )
            Text(
                label,
                color     = Color.White.copy(alpha = 0.85f),
                fontSize  = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(NTABlue)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TableCell("Q.No",        0.10f, Color.White, bold = true)
        TableCell("Selected",    0.15f, Color.White, bold = true)
        TableCell("Status",      0.35f, Color.White, bold = true)
        TableCell("Correct Ans", 0.20f, Color.White, bold = true)
        TableCell("Marks",       0.20f, Color.White, bold = true)
    }
}

@Composable
private fun TableRow(
    questionId: Int,
    selectedOption: String,
    status: String,
    statusColor: Color,
    correctOption: String,
    marks: String,
    marksColor: Color
) {
    val bg = if (questionId % 2 == 0) Color.White else Color(0xFFF9FAFB)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TableCell(questionId.toString(), 0.10f, NTABlue,          bold = true)
        TableCell(selectedOption,        0.15f, Color(0xFF374151))
        TableCell(status,                0.35f, statusColor,       bold = true)
        TableCell(correctOption,         0.20f, NTAGreen,          bold = true)
        TableCell(marks,                 0.20f, marksColor,        bold = true)
    }
}

@Composable
private fun RowScope.TableCell(
    text: String,
    weight: Float,
    color: Color,
    bold: Boolean = false
) {
    Text(
        text       = text,
        modifier   = Modifier.weight(weight),
        fontSize   = 11.sp,
        color      = color,
        fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        textAlign  = TextAlign.Center
    )
}
