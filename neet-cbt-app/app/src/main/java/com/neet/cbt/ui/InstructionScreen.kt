package com.neet.cbt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.ui.components.AnsweredIcon
import com.neet.cbt.ui.components.AnsweredMarkedIcon
import com.neet.cbt.ui.components.MarkedIcon
import com.neet.cbt.ui.components.NotAnsweredIcon
import com.neet.cbt.ui.components.NotVisitedIcon
import com.neet.cbt.viewmodel.ExamViewModel
import com.neet.cbt.viewmodel.Screen

@Composable
fun InstructionScreen(vm: ExamViewModel) {
    val accepted by vm.instructionsAccepted.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        NTAHeader()

        // ── Title bar ─────────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "GENERAL INSTRUCTIONS",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1A3A6B),
                modifier = Modifier.weight(1f)
            )
            Text("Choose Your Default Language", fontSize = 11.sp, color = Color(0xFF6B7280))
            Spacer(Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .border(1.dp, Color(0xFFD1D5DB), RoundedCornerShape(3.dp))
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("English", fontSize = 12.sp, color = Color(0xFF374151))
                    Text(" ▼", fontSize = 10.sp, color = Color(0xFF6B7280))
                }
            }
        }

        HorizontalDivider(color = Color(0xFFE5E7EB))

        // ── Scrollable instructions ───────────────────────────────────────────
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // "Please read…" header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Please read the instructions carefully",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color(0xFF1A3A6B), RoundedCornerShape(2.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("▶", color = Color.White, fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(14.dp))
            SectionHeader("General Instructions:")

            InstrNum("1.", buildAnnotatedString {
                append("Total duration of NEET - BIOLOGY is 180 min.")
            }.toString())

            InstrNum(
                "2.",
                "The clock will be set at the server. The countdown timer in the top right corner of screen " +
                "will display the remaining time available for you to complete the examination. When the timer " +
                "reaches zero, the examination will end by itself. You will not be required to end or submit your examination."
            )

            InstrNum(
                "3.",
                "The Questions Palette displayed on the right side of screen will show the status of each " +
                "question using one of the following symbols:"
            )

            Spacer(Modifier.height(8.dp))

            // Status legend items
            StatusLegendRow(
                icon = { NotVisitedIcon(24.dp) },
                text = "You have not visited the question yet."
            )
            Spacer(Modifier.height(8.dp))
            StatusLegendRow(
                icon = { NotAnsweredIcon(24.dp) },
                text = "You have not answered the question."
            )
            Spacer(Modifier.height(8.dp))
            StatusLegendRow(
                icon = { AnsweredIcon(24.dp) },
                text = "You have answered the question."
            )
            Spacer(Modifier.height(8.dp))
            StatusLegendRow(
                icon = { MarkedIcon(24.dp) },
                text = "You have NOT answered the question, but have marked the question for review."
            )
            Spacer(Modifier.height(8.dp))
            StatusLegendRow(
                icon = { AnsweredMarkedIcon(24.dp) },
                text = "The question(s) \"Answered and Marked for Review\" will be considered for evaluation."
            )
            Spacer(Modifier.height(12.dp))

            InstrNum(
                "4.",
                "You can click on the \">\" arrow which appears to the left of question palette to collapse the question " +
                "palette thereby maximizing the question window. To view the question palette again, you can click on " +
                "\"<\" which appears on the right side of question window."
            )

            InstrNum(
                "5.",
                "You can click on your \"Profile\" image on top right corner of your screen to change the language during " +
                "the exam for entire question paper. On clicking of Profile image you will get a drop-down to change the " +
                "question content to the desired language."
            )

            InstrNum(
                "6.",
                "You can click on ⬇ to navigate to the bottom and ⬆ to navigate to top of the question, without scrolling."
            )

            Spacer(Modifier.height(8.dp))
            SectionHeader("Navigating to a Question:")

            InstrNum(
                "7.",
                "To answer a question, do the following:\n" +
                "  a. Click on the question number in the Question Palette at the right of your screen to go to that " +
                "numbered question directly. Note that using this option does NOT save your answer to the current question.\n" +
                "  b. Click on Save & Next to save your answer for the current question and then go to the next question.\n" +
                "  c. Click on Mark for Review & Next to save your answer for the current question, mark it for review, and then go to the next question."
            )

            Spacer(Modifier.height(8.dp))
            SectionHeader("Answering a Question:")

            InstrNum(
                "8.",
                "Procedure for answering a multiple choice type question:\n" +
                "  a. To select you answer, click on the button of one of the options.\n" +
                "  b. To deselect your chosen answer, click on the button of the chosen option again or click on the Clear Response button.\n" +
                "  c. To change your chosen answer, click on the button of another option.\n" +
                "  d. To save your answer, you MUST click on the Save & Next button.\n" +
                "  e. To mark the question for review, click on the Mark for Review & Next button."
            )

            InstrNum(
                "9.",
                "To change your answer to a question that has already been answered, first select that question for " +
                "answering and then follow the procedure for answering that type of question."
            )

            Spacer(Modifier.height(8.dp))
            SectionHeader("Navigating through sections:")

            InstrNum(
                "10.",
                "Sections in this question paper are displayed on the top bar of the screen. Questions in a section can " +
                "be viewed by click on the section name. The section you are currently viewing is highlighted."
            )

            InstrNum(
                "11.",
                "After click the Save & Next button on the last question for a section, you will automatically be taken " +
                "to the first question of the next section."
            )

            InstrNum(
                "12.",
                "You can shuffle between sections and questions anytime during the examination as per your convenience " +
                "only during the time stipulated."
            )

            InstrNum(
                "13.",
                "Candidate can view the corresponding section summary as part of the legend that appears in every section above the question palette."
            )

            Spacer(Modifier.height(16.dp))

            // ── Checkbox + Proceed ────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(4.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = accepted,
                    onCheckedChange = { vm.setInstructionsAccepted(it) },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF2563EB))
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "I have read and understood the instructions and wish to proceed.",
                    fontSize = 12.sp,
                    color = Color(0xFF374151)
                )
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { vm.navigate(Screen.Exam) },
                enabled = accepted,
                modifier = Modifier.align(Alignment.CenterHorizontally).height(40.dp),
                shape = RoundedCornerShape(3.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2563EB),
                    disabledContainerColor = Color(0xFF93C5FD)
                )
            ) {
                Text("PROCEED  ▶", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))
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
private fun SectionHeader(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 13.sp,
        color = Color(0xFF1F2937),
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
private fun InstrNum(number: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(number, fontSize = 12.sp, fontWeight = FontWeight.Medium, modifier = Modifier.width(26.dp))
        Text(text, fontSize = 12.sp, color = Color(0xFF374151), lineHeight = 18.sp, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun StatusLegendRow(icon: @Composable () -> Unit, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 26.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(Modifier.width(10.dp))
        Text(text, fontSize = 12.sp, color = Color(0xFF374151), lineHeight = 17.sp)
    }
}
