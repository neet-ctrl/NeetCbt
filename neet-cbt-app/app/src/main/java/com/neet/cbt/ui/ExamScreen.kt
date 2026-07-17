package com.neet.cbt.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.ui.components.BottomActionBar
import com.neet.cbt.ui.components.QuestionPalette
import com.neet.cbt.ui.components.TimerDisplay
import com.neet.cbt.ui.theme.*
import com.neet.cbt.viewmodel.ExamViewModel
import com.neet.cbt.viewmodel.Screen

@Composable
fun ExamScreen(vm: ExamViewModel) {
    val exam by vm.exam.collectAsState()
    val currentSectionIdx by vm.currentSectionIndex.collectAsState()
    val currentQIdx by vm.currentQuestionIndexInSection.collectAsState()
    val tempSelection by vm.tempSelection.collectAsState()
    val answers by vm.answers.collectAsState()
    val bitmap by vm.currentBitmap.collectAsState()
    var showSubmitDialog by remember { mutableStateOf(false) }

    val currentSection = exam.sections[currentSectionIdx]
    val currentQuestion = currentSection.questions[currentQIdx]

    // Load bitmap when question changes
    LaunchedEffect(currentQuestion.id) {
        vm.loadBitmapForCurrentQuestion()
    }

    Column(modifier = Modifier.fillMaxSize().background(NTALightGrey)) {
        // ── Top Header ───────────────────────────────────────────────────────
        NTAHeader()

        // ── Candidate Info Bar ────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NTAHeaderBg)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                InfoRow("Candidate Name", "Your Name")
                InfoRow("Exam Name", "NEET")
                InfoRow("Subject Name", currentSection.name, valueColor = Color(0xFF2563EB))
            }

            Column(horizontalAlignment = Alignment.End) {
                TimerDisplay(vm = vm)
                Spacer(modifier = Modifier.height(4.dp))
                // Language selector placeholder
                Box(
                    modifier = Modifier
                        .border(1.dp, NTABorder, RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text("English", fontSize = 11.sp, color = Color(0xFF374151))
                }
            }
        }

        // ── Section Tabs ─────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            exam.sections.forEachIndexed { idx, section ->
                val isActive = idx == currentSectionIdx
                Box(
                    modifier = Modifier
                        .clickable { vm.navigateToQuestion(idx, 0) }
                        .background(if (isActive) NTABlue else Color.Transparent)
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = section.name,
                        color = if (isActive) Color.White else NTABlue,
                        fontSize = 12.sp,
                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                    )
                }
                if (idx < exam.sections.size - 1) {
                    Box(modifier = Modifier.width(1.dp).height(24.dp).background(NTABorder))
                }
            }
        }
        HorizontalDivider(color = NTABorder)

        // ── Main Body: Question + Palette ────────────────────────────────────
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // ── Question Area (left, ~70%) ─────────────────────────────────
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Question header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Question ${currentQuestion.id}:",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = NTABlue
                        )
                        HorizontalDivider(
                            modifier = Modifier.weight(1f).padding(horizontal = 12.dp),
                            color = NTABorder
                        )
                    }

                    // Question text
                    Text(
                        text = currentQuestion.text,
                        fontSize = 13.sp,
                        color = Color(0xFF1F2937),
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // PDF page image (for questions with figures)
                    if (currentQuestion.hasImage && bitmap != null) {
                        Image(
                            bitmap = bitmap!!.asImageBitmap(),
                            contentDescription = "Question ${currentQuestion.id} figure",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 220.dp)
                                .padding(bottom = 12.dp),
                            contentScale = ContentScale.Fit
                        )
                    } else if (currentQuestion.hasImage) {
                        // Loading placeholder
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(NTALightGrey),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "[Figure: Loading from question paper...]",
                                fontSize = 11.sp,
                                color = NTADarkGrey,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // ── Options ──────────────────────────────────────────────
                    currentQuestion.options.forEachIndexed { index, optionText ->
                        val label = listOf("A", "B", "C", "D")[index]
                        val isSelected = tempSelection == index
                        val isSaved = answers[currentQuestion.id] == index

                        OptionRow(
                            label = label,
                            text = optionText,
                            isSelected = isSelected,
                            isSaved = isSaved,
                            onClick = { vm.selectOption(index) }
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    // Dropped question notice
                    if (currentQuestion.correctOption == -1) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF9C3)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "⚠ This question was DROPPED by NTA. Full marks (+4) will be awarded to all candidates.",
                                fontSize = 11.sp,
                                color = Color(0xFF92400E),
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }

                HorizontalDivider(color = NTABorder)

                // ── Bottom Action Bar ────────────────────────────────────────
                BottomActionBar(
                    vm = vm,
                    onSubmitClick = { showSubmitDialog = true }
                )
            }

            // ── Palette (right, ~30%) ──────────────────────────────────────
            QuestionPalette(vm = vm)
        }
    }

    // ── Submit Confirmation Dialog ──────────────────────────────────────────
    if (showSubmitDialog) {
        SubmitConfirmDialog(
            vm = vm,
            onDismiss = { showSubmitDialog = false },
            onConfirm = {
                showSubmitDialog = false
                vm.stopTimer()
                vm.navigate(Screen.Result)
            }
        )
    }
}

@Composable
private fun OptionRow(
    label: String,
    text: String,
    isSelected: Boolean,
    isSaved: Boolean,
    onClick: () -> Unit
) {
    val bgColor = when {
        isSelected && isSaved -> Color(0xFFDCFCE7)
        isSelected            -> Color(0xFFEFF6FF)
        isSaved               -> Color(0xFFDCFCE7)
        else                  -> Color.White
    }
    val borderColor = when {
        isSelected -> NTALightBlue
        isSaved    -> NTAGreen
        else       -> NTABorder
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(6.dp))
            .selectable(selected = isSelected, onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = NTALightBlue,
                unselectedColor = NTADarkGrey
            ),
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "($label)  $text",
            fontSize = 13.sp,
            color = Color(0xFF1F2937),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String, valueColor: Color = Color(0xFF1F2937)) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label : ",
            fontSize = 11.sp,
            color = NTADarkGrey
        )
        Text(
            text = value,
            fontSize = 11.sp,
            color = valueColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun SubmitConfirmDialog(
    vm: ExamViewModel,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val summary = vm.getSummaryCount()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Exam Summary",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = NTABlue
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                SummaryRow("Total Questions", summary.total.toString())
                SummaryRow("Answered", summary.answered.toString(), Color(0xFF16A34A))
                SummaryRow("Not Answered", summary.notAnswered.toString(), Color(0xFFDC2626))
                SummaryRow("Marked for Review", summary.markedForReview.toString(), Color(0xFF7C3AED))
                SummaryRow("Answered & Marked", summary.answeredAndMarked.toString(), Color(0xFF7C3AED))
                SummaryRow("Not Visited", summary.notVisited.toString(), Color(0xFF6B7280))

                Spacer(modifier = Modifier.height(6.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "⚠ Are you sure you want to submit for final marking? No changes will be allowed after submission.",
                    fontSize = 12.sp,
                    color = Color(0xFFDC2626),
                    fontWeight = FontWeight.Medium
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = NTAGreen),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("YES – SUBMIT", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("NO – CONTINUE", color = NTABlue)
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
private fun SummaryRow(label: String, value: String, valueColor: Color = Color(0xFF1F2937)) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 13.sp, color = Color(0xFF374151))
        Text(value, fontSize = 13.sp, color = valueColor, fontWeight = FontWeight.Bold)
    }
}

// Note: clip() is imported from androidx.compose.ui.draw
