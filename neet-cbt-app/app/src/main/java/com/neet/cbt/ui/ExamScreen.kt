package com.neet.cbt.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.ui.components.BottomActionBar
import com.neet.cbt.ui.components.QuestionPalette
import com.neet.cbt.ui.components.TimerDisplay
import com.neet.cbt.viewmodel.ExamViewModel
import com.neet.cbt.viewmodel.Screen

@Composable
fun ExamScreen(vm: ExamViewModel) {
    val exam              by vm.exam.collectAsState()
    val currentSectionIdx by vm.currentSectionIndex.collectAsState()
    val currentQIdx       by vm.currentQuestionIndexInSection.collectAsState()
    val tempSelection     by vm.tempSelection.collectAsState()
    val answers           by vm.answers.collectAsState()
    val bitmap            by vm.currentBitmap.collectAsState()
    val paletteVisible    by vm.paletteVisible.collectAsState()

    val currentSection  = exam.sections[currentSectionIdx]
    val currentQuestion = currentSection.questions[currentQIdx]

    LaunchedEffect(currentQuestion.id) { vm.loadBitmapForCurrentQuestion() }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        // ── NTA Header ────────────────────────────────────────────────────────
        NTAHeader()

        // ── Candidate Info Bar ────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F4FF))
                .border(1.dp, Color(0xFFE5E7EB))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Photo box
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .border(1.dp, Color(0xFF9CA3AF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, null, Modifier.size(42.dp), Color(0xFF9CA3AF))
            }

            Spacer(Modifier.width(10.dp))

            // Candidate info text
            Column(modifier = Modifier.weight(1f)) {
                InfoRow("Candidate Name", "Your Name", valueColor = Color(0xFFEA580C))
                InfoRow("Exam Name     ", "NEET", valueColor = Color(0xFF1F2937))
                InfoRow("Subject Name  ", currentSection.name, valueColor = Color(0xFFEA580C))
                Spacer(Modifier.height(2.dp))
                TimerDisplay(vm = vm)
            }

            // English dropdown (right)
            Box(
                modifier = Modifier
                    .border(1.dp, Color(0xFFD1D5DB), RoundedCornerShape(3.dp))
                    .background(Color.White)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text("English ▼", fontSize = 11.sp, color = Color(0xFF374151))
            }
        }

        // ── Main Area: Question + Palette ─────────────────────────────────────
        Row(modifier = Modifier.weight(1f).fillMaxWidth()) {

            // ── Question panel ─────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    // Question header: "Question N:" + scroll-down icon
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Question ${currentQuestion.id}:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A3A6B)
                        )
                        Spacer(Modifier.weight(1f))
                        // Blue circle scroll-down button
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(Color(0xFF2563EB), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.KeyboardArrowDown,
                                null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    HorizontalDivider(color = Color(0xFFD1D5DB), thickness = 1.dp)
                    Spacer(Modifier.height(8.dp))

                    // Dropped-question notice (Q40)
                    if (currentQuestion.correctOption == -2) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF9C3)),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        ) {
                            Text(
                                "⚠  This question was DROPPED by NTA. Full marks (+4) awarded to ALL candidates.",
                                fontSize = 10.sp,
                                color = Color(0xFF92400E),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    // PDF figure image (if applicable)
                    if (currentQuestion.hasImage) {
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap!!.asImageBitmap(),
                                contentDescription = "Figure for Q${currentQuestion.id}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 180.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .padding(bottom = 8.dp),
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .background(Color(0xFFF3F4F6), RoundedCornerShape(3.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color(0xFF2563EB),
                                    strokeWidth = 2.dp
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                        }
                    }

                    // Question stem text
                    Text(
                        text = "${currentQuestion.id}. ${currentQuestion.text}",
                        fontSize = 12.sp,
                        color = Color(0xFF1F2937),
                        lineHeight = 18.sp
                    )

                    Spacer(Modifier.height(10.dp))

                    // ── Options in 2×2 grid (NTA style: text display only) ─────
                    val options = currentQuestion.options
                    val labels = listOf("A", "B", "C", "D")

                    // Row 1: A and B
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OptionTextCell("A", options.getOrElse(0) { "" }, Modifier.weight(1f))
                        OptionTextCell("B", options.getOrElse(1) { "" }, Modifier.weight(1f))
                    }
                    // Row 2: C and D
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OptionTextCell("C", options.getOrElse(2) { "" }, Modifier.weight(1f))
                        OptionTextCell("D", options.getOrElse(3) { "" }, Modifier.weight(1f))
                    }

                    Spacer(Modifier.height(12.dp))

                    // ── Radio buttons: ○ 1) ○ 2) ○ 3) ○ 4) ─────────────────────
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 0..3) {
                            val isSelected = tempSelection == i
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { vm.selectOption(i) }
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { vm.selectOption(i) },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color(0xFF2563EB),
                                        unselectedColor = Color(0xFF6B7280)
                                    ),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(2.dp))
                                Text("${i + 1})", fontSize = 13.sp, color = Color(0xFF374151))
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(color = Color(0xFFD1D5DB), thickness = 1.dp)
                    Spacer(Modifier.height(6.dp))

                    // Blue circle scroll-up button
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(Color(0xFF2563EB), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.KeyboardArrowUp, null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                }

                HorizontalDivider(color = Color(0xFFE5E7EB))

                // ── Bottom action bar ───────────────────────────────────────────
                BottomActionBar(
                    vm = vm,
                    onSubmitClick = { vm.navigate(Screen.Summary) }
                )
            }

            // ── Palette column ─────────────────────────────────────────────────
            QuestionPalette(vm = vm)
        }

        // ── Footer ────────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A3A6B))
                .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("© All Rights Reserved - National Testing Agency", fontSize = 10.sp, color = Color.White)
        }
    }
}

// ─── Private helpers ──────────────────────────────────────────────────────────

@Composable
private fun OptionTextCell(label: String, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(vertical = 3.dp, horizontal = 2.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            "($label) ",
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF374151)
        )
        Text(
            text,
            fontSize = 11.sp,
            color = Color(0xFF1F2937),
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String, valueColor: Color = Color(0xFF1F2937)) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("$label : ", fontSize = 11.sp, color = Color(0xFF374151))
        Text(value, fontSize = 11.sp, color = valueColor, fontWeight = FontWeight.SemiBold)
    }
}
