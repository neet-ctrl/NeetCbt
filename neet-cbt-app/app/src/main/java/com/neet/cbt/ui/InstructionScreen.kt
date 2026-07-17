package com.neet.cbt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.ui.theme.*
import com.neet.cbt.viewmodel.ExamViewModel
import com.neet.cbt.viewmodel.Screen

@Composable
fun InstructionScreen(vm: ExamViewModel) {
    val accepted by vm.instructionsAccepted.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NTAHeader()

        // Title bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NTALightGrey)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "GENERAL INSTRUCTIONS",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = NTABlue,
                modifier = Modifier.weight(1f)
            )
            Text("Choose Your Default Language", fontSize = 12.sp, color = NTADarkGrey)
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .border(1.dp, NTABorder, RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("English", fontSize = 12.sp)
            }
        }

        // Scrollable content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Please read the instructions carefully",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "General Instructions:",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = Color(0xFF1F2937)
            )
            Spacer(modifier = Modifier.height(6.dp))

            InstructionItem("1.", "Total duration of NEET is 3 hours and 15 minutes (195 minutes).")
            InstructionItem(
                "2.",
                "The clock will be set at the server. The countdown timer in the top right corner of the screen " +
                "will display the remaining time available for you to complete the examination. When the timer " +
                "reaches zero, the examination will end by itself. You will not be required to end or submit your examination."
            )

            InstructionItem(
                "3.",
                "The Questions Palette displayed on the right side of screen will show the status of each question " +
                "using one of the following symbols:"
            )

            // Color legend
            Spacer(modifier = Modifier.height(8.dp))
            StatusLegendRow(color = StatusNotVisited, borderColor = Color.Gray, label = "You have not visited the question yet.")
            StatusLegendRow(color = StatusNotAnswered, label = "You have not answered the question.")
            StatusLegendRow(color = StatusAnswered, label = "You have answered the question.")
            StatusLegendRow(color = StatusMarkedForReview, label = "You have NOT answered the question, but have marked the question for review.")
            StatusLegendRow(color = StatusAnsweredAndMarked, dotColor = StatusAnswered, label = "The question(s) \"Answered and Marked for Review\" will be considered for evaluation.")
            Spacer(modifier = Modifier.height(8.dp))

            InstructionItem(
                "4.",
                "You can click on the \">\" arrow which appears to the left of question palette to collapse the " +
                "question palette thereby maximising the question window. To view the question palette again, " +
                "you can click on \"<\" which appears on the right side of question window."
            )
            InstructionItem(
                "5.",
                "You can click on your 'Profile' image on top right corner of your screen to change the language " +
                "during the exam for entire question paper."
            )
            InstructionItem(
                "6.",
                "You can click on the down arrow to navigate to the bottom and the up arrow to navigate to top " +
                "of the question, without scrolling."
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("Navigating to a Question:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(4.dp))
            InstructionItem(
                "7.",
                "To answer a question, do the following:\n" +
                "  a. Click on the question number in the Question Palette at the right of your screen to go to that " +
                "numbered question directly. Note that using this option does NOT save your answer to the current question.\n" +
                "  b. Click on Save & Next to save your answer for the current question and then go to the next question.\n" +
                "  c. Click on Mark for Review & Next to save your answer, mark it for review, and then go to the next question."
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("Answering a Question:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(4.dp))
            InstructionItem(
                "8.",
                "Procedure for answering a multiple choice type question:\n" +
                "  a. To select your answer, click on the button of one of the options.\n" +
                "  b. To deselect your chosen answer, click on the button of the chosen option again or click on the Clear Response button.\n" +
                "  c. To change your chosen answer, click on the button of another option.\n" +
                "  d. To save your answer, you MUST click the Save & Next button.\n" +
                "  e. To mark the question for review, click on the Mark for Review & Next button."
            )
            InstructionItem(
                "9.",
                "To change your answer to a question that has already been answered, first select that question " +
                "for answering and then follow the procedure for answering that type of question."
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("Navigating through sections:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(4.dp))
            InstructionItem(
                "10.",
                "Sections in this question paper are displayed on the top bar of the screen. Questions in a section " +
                "can be viewed by clicking on the section name. The section you are currently viewing is highlighted."
            )
            InstructionItem(
                "11.",
                "After clicking the Save & Next button on the last question for a section, you will automatically be " +
                "taken to the first question of the next section."
            )
            InstructionItem(
                "12.",
                "You can shuffle between sections and questions anytime during the examination as per your convenience " +
                "only during the time stipulated."
            )
            InstructionItem(
                "13.",
                "Candidate can view the corresponding section summary as part of the legend that appears in every " +
                "section above the question palette."
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Marks info
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Marking Scheme:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("• Correct Answer: +4 marks", fontSize = 12.sp)
                    Text("• Wrong Answer: −1 mark (negative marking)", fontSize = 12.sp)
                    Text("• Unattempted: 0 marks", fontSize = 12.sp)
                    Text("• Total Questions: 180 | Maximum Marks: 720", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Declaration checkbox
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Checkbox(
                    checked = accepted,
                    onCheckedChange = { vm.setInstructionsAccepted(it) },
                    colors = CheckboxDefaults.colors(checkedColor = NTALightBlue)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "I have read and understood the instructions. All computer hardware allotted to me are in " +
                    "proper working condition. I declare that I am not in possession of / not wearing / not carrying " +
                    "any prohibited gadget like mobile phone, bluetooth devices etc. /any prohibited material with me " +
                    "into the Examination Hall. I agree that in case of not adhering to the instructions, I shall be " +
                    "liable to be debarred from this Test and/or to disciplinary action, which may include ban from " +
                    "future Tests / Examinations.",
                    fontSize = 11.sp,
                    color = Color(0xFF374151),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Bottom bar with PROCEED button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NTALightGrey)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    vm.navigate(Screen.Exam)
                    vm.startTimer()
                },
                enabled = accepted,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NTALightBlue,
                    disabledContainerColor = Color(0xFFBFDBFE)
                ),
                modifier = Modifier.height(40.dp)
            ) {
                Text(
                    "I am ready to begin",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun InstructionItem(number: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = number,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(28.dp),
            color = Color(0xFF374151)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color(0xFF374151),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatusLegendRow(
    color: Color,
    borderColor: Color = Color.Transparent,
    dotColor: Color? = null,
    label: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
                .border(if (borderColor != Color.Transparent) 1.dp else 0.dp, borderColor, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.BottomEnd
        ) {
            if (dotColor != null) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(dotColor)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label, fontSize = 12.sp, color = Color(0xFF374151))
    }
}
