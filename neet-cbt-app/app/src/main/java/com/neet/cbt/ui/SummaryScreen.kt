package com.neet.cbt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import com.neet.cbt.ui.components.TimerDisplay
import com.neet.cbt.viewmodel.ExamViewModel
import com.neet.cbt.viewmodel.Screen

@Composable
fun SummaryScreen(vm: ExamViewModel) {
    val exam    by vm.exam.collectAsState()
    val statuses by vm.statuses.collectAsState()

    val allQs       = exam.sections.flatMap { it.questions }
    val total       = allQs.size
    val answered    = allQs.count { statuses[it.id] == QuestionStatus.ANSWERED }
    val notAnswered = allQs.count { statuses[it.id] == QuestionStatus.NOT_ANSWERED }
    val marked      = allQs.count { statuses[it.id] == QuestionStatus.MARKED_FOR_REVIEW }
    val answeredMkd = allQs.count { statuses[it.id] == QuestionStatus.ANSWERED_AND_MARKED }
    val notVisited  = allQs.count { statuses[it.id] == null || statuses[it.id] == QuestionStatus.NOT_VISITED }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        NTAHeader()

        // ── Candidate Info Bar ────────────────────────────────────────────────
        CandidateInfoBar(vm)

        // ── Exam Summary ──────────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Exam Summary",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // ── Summary Table ─────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFD1D5DB), RoundedCornerShape(4.dp))
            ) {
                Column {
                    // Header row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF3F4F6))
                    ) {
                        SummaryHeaderCell("No of\nQuestions",    Modifier.weight(1f))
                        SummaryHeaderCell("Answered",            Modifier.weight(1f))
                        SummaryHeaderCell("Not\nAnswered",       Modifier.weight(1f))
                        SummaryHeaderCell("Marked for\nReview",  Modifier.weight(1f))
                        SummaryHeaderCell(
                            "Answered & Marked for Review\n(will be considered for evaluation)",
                            Modifier.weight(1.8f)
                        )
                        SummaryHeaderCell("Not\nVisited",        Modifier.weight(1f))
                    }

                    HorizontalDivider(color = Color(0xFFD1D5DB))

                    // Values row
                    Row(modifier = Modifier.fillMaxWidth()) {
                        SummaryValueCell("$total",       Modifier.weight(1f))
                        SummaryValueCell("$answered",    Modifier.weight(1f), Color(0xFF16A34A))
                        SummaryValueCell("$notAnswered", Modifier.weight(1f), Color(0xFFEF4444))
                        SummaryValueCell("$marked",      Modifier.weight(1f), Color(0xFF7C3AED))
                        SummaryValueCell("$answeredMkd", Modifier.weight(1.8f), Color(0xFFD97706))
                        SummaryValueCell("$notVisited",  Modifier.weight(1f))
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // ── Confirmation text ──────────────────────────────────────────────
            Text(
                "Are you sure you want to submit for final marking?\nNo changes will be allowed after submission.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(24.dp))

            // ── YES / NO buttons ──────────────────────────────────────────────
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedButton(
                    onClick = { vm.navigate(Screen.Exam) },
                    modifier = Modifier.width(90.dp).height(38.dp),
                    shape = RoundedCornerShape(3.dp),
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Text("NO", fontWeight = FontWeight.Bold, color = Color(0xFF374151))
                }

                OutlinedButton(
                    onClick = {
                        vm.stopTimer()
                        vm.navigate(Screen.Result)
                    },
                    modifier = Modifier.width(90.dp).height(38.dp),
                    shape = RoundedCornerShape(3.dp),
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Text("YES", fontWeight = FontWeight.Bold, color = Color(0xFF374151))
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

// ─── Shared candidate bar (reused across Summary & Result) ────────────────────
@Composable
fun CandidateInfoBar(vm: ExamViewModel) {
    val exam              by vm.exam.collectAsState()
    val currentSectionIdx by vm.currentSectionIndex.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F4FF))
            .border(1.dp, Color(0xFFE5E7EB))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(52.dp).border(1.dp, Color(0xFF9CA3AF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, null, Modifier.size(38.dp), Color(0xFF9CA3AF))
        }
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            InfoBarRow("Candidate Name", "Your Name",  Color(0xFFEA580C))
            InfoBarRow("Exam Name     ", "NEET",       Color(0xFF1F2937))
            InfoBarRow("Subject Name  ", exam.sections[currentSectionIdx].name, Color(0xFFEA580C))
            Spacer(Modifier.height(2.dp))
            TimerDisplay(vm = vm)
        }
    }
}

@Composable
private fun InfoBarRow(label: String, value: String, valueColor: Color = Color(0xFF1F2937)) {
    Row {
        Text("$label : ", fontSize = 11.sp, color = Color(0xFF374151))
        Text(value, fontSize = 11.sp, color = valueColor, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun SummaryHeaderCell(text: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .border(0.5.dp, Color(0xFFD1D5DB))
            .padding(horizontal = 6.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF374151),
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}

@Composable
private fun SummaryValueCell(text: String, modifier: Modifier, color: Color = Color(0xFF1F2937)) {
    Box(
        modifier = modifier
            .border(0.5.dp, Color(0xFFD1D5DB))
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = color)
    }
}
