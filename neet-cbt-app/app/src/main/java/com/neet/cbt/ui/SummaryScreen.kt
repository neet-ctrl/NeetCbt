package com.neet.cbt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.ui.theme.*
import com.neet.cbt.viewmodel.ExamViewModel
import com.neet.cbt.viewmodel.Screen

@Composable
fun SummaryScreen(vm: ExamViewModel) {
    val summary = vm.getSummaryCount()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NTALightGrey)
    ) {
        NTAHeader()

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.width(420.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Exam Summary",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = NTABlue
                    )

                    HorizontalDivider()

                    SummaryStatRow("Total Questions", summary.total.toString())
                    SummaryStatRow("Answered", summary.answered.toString(), NTAGreen)
                    SummaryStatRow("Not Answered", summary.notAnswered.toString(), NTARed)
                    SummaryStatRow("Marked for Review", summary.markedForReview.toString(), NTAPurple)
                    SummaryStatRow("Answered & Marked for Review", summary.answeredAndMarked.toString(), NTAPurple)
                    SummaryStatRow("Not Visited", summary.notVisited.toString(), NTADarkGrey)

                    HorizontalDivider()

                    Text(
                        text = "⚠ Are you sure you want to submit for final marking?\nNo changes will be allowed after submission.",
                        fontSize = 12.sp,
                        color = NTARed,
                        fontWeight = FontWeight.Medium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                    ) {
                        OutlinedButton(
                            onClick = { vm.navigate(Screen.Exam) },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.weight(1f).height(44.dp)
                        ) {
                            Text("NO – CONTINUE", color = NTABlue, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                vm.stopTimer()
                                vm.navigate(Screen.Result)
                            },
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = NTAGreen),
                            modifier = Modifier.weight(1f).height(44.dp)
                        ) {
                            Text("YES – SUBMIT", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryStatRow(label: String, value: String, valueColor: Color = Color(0xFF1F2937)) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(NTALightGrey, RoundedCornerShape(6.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 14.sp, color = Color(0xFF374151))
        Text(
            value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
    }
}
