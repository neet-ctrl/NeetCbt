package com.neet.cbt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.ui.theme.NTABlue

/**
 * Shared NTA top header bar used across all screens.
 */
@Composable
fun NTAHeader() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: NTA branding
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Placeholder NTA logo text
                Column {
                    Text(
                        text = "राष्ट्रीय परीक्षा एजेंसी",
                        fontSize = 10.sp,
                        color = NTABlue,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "National Testing Agency",
                        fontSize = 12.sp,
                        color = NTABlue,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Excellence in Assessment",
                        fontSize = 9.sp,
                        color = Color(0xFF16A34A),
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            // Center: Ministry of Education
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Ministry of Education",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
                Text(
                    text = "Government of India",
                    fontSize = 10.sp,
                    color = Color(0xFF6B7280)
                )
            }

            // Right: Azadi Ka Amrit Mahotsav placeholder
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Azadi Ka",
                    fontSize = 9.sp,
                    color = Color(0xFFFF6B00),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Amrit Mahotsav",
                    fontSize = 8.sp,
                    color = Color(0xFF1A3A6B)
                )
            }
        }
        HorizontalDivider(color = NTABlue, thickness = 2.dp)
    }
}
