package com.neet.cbt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.viewmodel.ExamViewModel

@Composable
fun TimerDisplay(vm: ExamViewModel) {
    val remaining by vm.remainingSeconds.collectAsState()

    val hours   = remaining / 3600
    val minutes = (remaining % 3600) / 60
    val seconds = remaining % 60

    val color = when {
        remaining <= 300  -> Color(0xFFDC2626) // Red when ≤ 5 min
        remaining <= 600  -> Color(0xFFD97706) // Orange when ≤ 10 min
        else              -> Color(0xFF059669) // Green normal
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Remaining Time : ",
            fontSize = 12.sp,
            color = Color(0xFF374151)
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(color)
                .padding(horizontal = 10.dp, vertical = 3.dp)
        ) {
            Text(
                text = "%02d:%02d:%02d".format(hours, minutes, seconds),
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}
