package com.neet.cbt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NTAHeader() {
    Column(Modifier.fillMaxWidth()) {

        // ── Top thin nav bar ─────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0F0))
                .padding(horizontal = 10.dp, vertical = 3.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home
            Row(
                modifier = Modifier
                    .background(Color(0xFF3B7D3B), RoundedCornerShape(3.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Home, null, Modifier.size(13.dp), Color.White)
                Spacer(Modifier.width(3.dp))
                Text("Home", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            }
            Spacer(Modifier.width(6.dp))
            // English
            Row(
                modifier = Modifier
                    .background(Color(0xFF5B21B6), RoundedCornerShape(14.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🌐", fontSize = 10.sp)
                Spacer(Modifier.width(3.dp))
                Text("English ▼", color = Color.White, fontSize = 11.sp)
            }
            Spacer(Modifier.width(6.dp))
            // i circle
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(0xFF5B21B6), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("i", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(4.dp))
            // Edit icon
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(0xFF9CA3AF), RoundedCornerShape(3.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("✎", color = Color.White, fontSize = 12.sp)
            }
        }

        // ── Main logo bar ─────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 150 Years circular badge
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .border(2.dp, Color(0xFFB8860B), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("150", fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF92400E), lineHeight = 11.sp)
                    Text("YEARS", fontSize = 5.sp, color = Color(0xFF92400E), letterSpacing = 0.5.sp)
                    Text("OF", fontSize = 5.sp, color = Color(0xFF92400E))
                    Text("CELEBRATING", fontSize = 4.sp, color = Color(0xFF92400E))
                }
            }

            Spacer(Modifier.width(8.dp))

            // NTA logo + text
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFFF6B00), CircleShape)
                        .border(1.dp, Color(0xFFFF8C00), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✿", color = Color.White, fontSize = 18.sp)
                }
                Spacer(Modifier.width(6.dp))
                Column {
                    Text(
                        "राष्ट्रीय परीक्षा एजेंसी",
                        fontSize = 10.sp,
                        color = Color(0xFF1A3A6B),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        "National Testing Agency",
                        fontSize = 12.sp,
                        color = Color(0xFF1A3A6B),
                        fontWeight = FontWeight.ExtraBold
                    )
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF007A3D), RoundedCornerShape(2.dp))
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text("Excellence in Assessment", fontSize = 7.sp, color = Color.White, fontStyle = FontStyle.Italic)
                    }
                }
            }

            // Separator
            Spacer(Modifier.width(10.dp))
            Box(Modifier.width(1.dp).height(48.dp).background(Color(0xFFD1D5DB)))
            Spacer(Modifier.width(10.dp))

            // Ministry of Education
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFFF5F0E0), CircleShape)
                        .border(1.dp, Color(0xFFD4A017), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🏛️", fontSize = 16.sp)
                }
                Spacer(Modifier.width(6.dp))
                Column {
                    Text("Ministry of Education", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
                    Text("Government of India", fontSize = 9.sp, color = Color(0xFF6B7280))
                }
            }

            Spacer(Modifier.weight(1f))

            // Azadi Ka Amrit Mahotsav
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0xFF0D4F2E), CircleShape)
                        .border(2.dp, Color(0xFFFF6B00), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("75", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, lineHeight = 14.sp)
                        Text("🇮🇳", fontSize = 8.sp)
                    }
                }
                Spacer(Modifier.width(4.dp))
                Column {
                    Text("Azadi Ka", fontSize = 9.sp, color = Color(0xFFFF6B00), fontWeight = FontWeight.Bold)
                    Text("Amrit Mahotsav", fontSize = 8.sp, color = Color(0xFF1A3A6B), fontWeight = FontWeight.Medium)
                }
            }
        }

        HorizontalDivider(color = Color(0xFFE5E7EB), thickness = 1.dp)
    }
}
