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
import com.neet.cbt.viewmodel.ExamViewModel

@Composable
fun ResultScreen(vm: ExamViewModel) {
    val exam    by vm.exam.collectAsState()
    val answers by vm.answers.collectAsState()
    val score   = remember { vm.getScoreResult() }

    // Two states: "Thank you" first, then "Score Card"
    var showScoreCard by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        NTAHeader()
        CandidateInfoBar(vm)

        if (!showScoreCard) {
            // ── Thank you submitted screen ──────────────────────────────────────
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(Modifier.height(20.dp))

                Text(
                    "Thank you, Submitted Successfully.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1F2937),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                OutlinedButton(
                    onClick = { showScoreCard = true },
                    shape = RoundedCornerShape(3.dp),
                    border = ButtonDefaults.outlinedButtonBorder,
                    modifier = Modifier.height(38.dp)
                ) {
                    Text("VIEW RESULT", fontWeight = FontWeight.Bold, color = Color(0xFF374151), fontSize = 13.sp)
                }
            }

        } else {
            // ── Score Card screen ───────────────────────────────────────────────
            Column(modifier = Modifier.weight(1f).fillMaxWidth()) {

                // Feedback banner row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFD1D5DB))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Please provide your valuable feedback about Mock Test:",
                        fontSize = 11.sp,
                        color = Color(0xFFDC2626),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(3.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text("STUDENT FEEDBACK", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.width(6.dp))
                    OutlinedButton(
                        onClick = { showScoreCard = false },
                        shape = RoundedCornerShape(3.dp),
                        border = ButtonDefaults.outlinedButtonBorder,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text("BACK", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
                    }
                }

                LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {

                    item {
                        // ── Score Card table ──────────────────────────────────
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .border(1.dp, Color(0xFFD1D5DB), RoundedCornerShape(4.dp))
                        ) {
                            Column {
                                // Title
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFF3F4F6))
                                        .padding(vertical = 8.dp),
                                    Alignment.Center
                                ) {
                                    Text("Scrore Card", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                                HorizontalDivider(color = Color(0xFFD1D5DB))

                                // Row 1
                                Row(Modifier.fillMaxWidth()) {
                                    ScoreCell("Total Question", "${score.total}", Modifier.weight(1f))
                                    ScoreCell("Total Attempted", "${score.attempted}", Modifier.weight(1f))
                                }
                                HorizontalDivider(color = Color(0xFFD1D5DB))

                                // Row 2
                                Row(Modifier.fillMaxWidth()) {
                                    ScoreCell("Correct Answers", "${score.correct}", Modifier.weight(1f))
                                    ScoreCell("Incorrect Answers", "${score.incorrect}", Modifier.weight(1f))
                                }
                                HorizontalDivider(color = Color(0xFFD1D5DB))

                                // Row 3 - Score
                                ScoreCell("Score", "${score.score}", Modifier.fillMaxWidth(), fontSize = 14)
                            }
                        }
                    }

                    item {
                        // ── Question analysis table header ─────────────────────
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp)
                                .border(1.dp, Color(0xFFD1D5DB), RoundedCornerShape(4.dp))
                        ) {
                            // Table header
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF3F4F6))
                            ) {
                                AnalysisHeaderCell("Question No.", Modifier.weight(1.2f))
                                AnalysisHeaderCell("selected Option", Modifier.weight(1f))
                                AnalysisHeaderCell("Status", Modifier.weight(1f))
                                AnalysisHeaderCell("Correct Option", Modifier.weight(1f))
                            }
                            HorizontalDivider(color = Color(0xFFD1D5DB))
                        }
                    }

                    // Question rows
                    val allQuestions = exam.sections.flatMap { it.questions }
                    items(allQuestions) { q ->
                        val selectedIdx = answers[q.id]
                        val isDropped   = q.correctOption == -2
                        val isAnswered  = selectedIdx != null
                        val isCorrect   = !isDropped && isAnswered &&
                                q.correctOption >= 0 && selectedIdx == q.correctOption

                        val statusText = when {
                            isDropped   -> "Dropped"
                            !isAnswered -> "N/A"
                            isCorrect   -> "Correct"
                            else        -> "Wrong"
                        }
                        val correctLabel = when {
                            isDropped            -> "Dropped"
                            q.correctOption >= 0 -> "${q.correctOption + 1}"
                            else                 -> "N/A"
                        }
                        val selectedLabel = selectedIdx?.let { "${it + 1}" } ?: "---"

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp)
                                .border(0.5.dp, Color(0xFFE5E7EB))
                                .background(if (q.id % 2 == 0) Color(0xFFF9FAFB) else Color.White)
                        ) {
                            AnalysisCell("Question ${q.id}:", Modifier.weight(1.2f))
                            AnalysisCell(selectedLabel, Modifier.weight(1f))
                            AnalysisCell(statusText, Modifier.weight(1f))
                            AnalysisCell(correctLabel, Modifier.weight(1f))
                        }
                    }

                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }

        // ── Footer ────────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A3A6B))
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("© All Rights Reserved - National Testing Agency", fontSize = 11.sp, color = Color.White)
        }
    }
}

// ─── Private helpers ──────────────────────────────────────────────────────────

@Composable
private fun ScoreCell(label: String, value: String, modifier: Modifier, fontSize: Int = 12) {
    Row(
        modifier = modifier
            .border(0.5.dp, Color(0xFFE5E7EB))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = fontSize.sp, color = Color(0xFF374151))
        Text(value, fontSize = fontSize.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
    }
}

@Composable
private fun AnalysisHeaderCell(text: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .border(0.5.dp, Color(0xFFD1D5DB))
            .padding(vertical = 10.dp, horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF374151),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RowScope.AnalysisCell(text: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .border(0.5.dp, Color(0xFFE5E7EB))
            .padding(horizontal = 6.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text, fontSize = 11.sp, color = Color(0xFF374151))
    }
}
