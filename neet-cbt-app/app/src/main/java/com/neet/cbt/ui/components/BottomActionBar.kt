package com.neet.cbt.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.ui.theme.*
import com.neet.cbt.viewmodel.ExamViewModel

@Composable
fun BottomActionBar(
    vm: ExamViewModel,
    onSubmitClick: () -> Unit
) {
    Surface(
        shadowElevation = 6.dp,
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            // Top row: SAVE & NEXT | CLEAR | SAVE & MARK | MARK & NEXT | SUBMIT
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // SAVE & NEXT
                ActionButton(
                    text = "SAVE & NEXT",
                    containerColor = NTAGreen,
                    modifier = Modifier.weight(1.6f)
                ) { vm.saveAndNext() }

                // CLEAR
                OutlinedButton(
                    onClick = { vm.clearAnswer() },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                ) {
                    Text("CLEAR", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NTADarkGrey)
                }

                // SAVE & MARK FOR REVIEW
                ActionButton(
                    text = "SAVE & MARK FOR REVIEW",
                    containerColor = NTAOrange,
                    modifier = Modifier.weight(2.2f)
                ) { vm.saveAndMarkForReview() }

                // MARK FOR REVIEW & NEXT
                ActionButton(
                    text = "MARK FOR REVIEW & NEXT",
                    containerColor = Color(0xFF9333EA),
                    modifier = Modifier.weight(2.2f)
                ) { vm.markForReviewAndNext() }

                // SUBMIT
                ActionButton(
                    text = "SUBMIT",
                    containerColor = NTAGreen,
                    modifier = Modifier.weight(1.2f)
                ) { onSubmitClick() }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Bottom row: << BACK | NEXT >>
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { vm.goBack() },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.height(32.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                ) {
                    Text("<< BACK", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = NTABlue)
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    onClick = { vm.goNext() },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.height(32.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                ) {
                    Text("NEXT >>", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = NTABlue)
                }
            }
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    containerColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        modifier = modifier.height(36.dp),
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 1
        )
    }
}
