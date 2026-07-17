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

/** Matching the NTA screenshot: "Remaining Time : [02:56:16]" where the time is a blue badge */
@Composable
fun TimerDisplay(vm: ExamViewModel) {
    val remaining by vm.remainingSeconds.collectAsState()

    val hours   = remaining / 3600
    val minutes = (remaining % 3600) / 60
    val seconds = remaining % 60

    val badgeColor = when {
        remaining <= 300  -> Color(0xFFDC2626)  // red ≤ 5 min
        remaining <= 600  -> Color(0xFFD97706)  // orange ≤ 10 min
        else              -> Color(0xFF2563EB)  // blue (NTA exact)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Remaining Time : ",
            fontSize = 11.sp,
            color = Color(0xFF374151),
            fontWeight = FontWeight.Normal
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(3.dp))
                .background(badgeColor)
                .padding(horizontal = 8.dp, vertical = 3.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "%02d:%02d:%02d".format(hours, minutes, seconds),
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                letterSpacing = 1.sp
            )
        }
    }
}
