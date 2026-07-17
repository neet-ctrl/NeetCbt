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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.viewmodel.ExamViewModel
import com.neet.cbt.viewmodel.Screen

@Composable
fun LoginScreen(vm: ExamViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        // ── NTA Header ────────────────────────────────────────────────────────
        NTAHeader()

        // ── System Info Bar ───────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .border(1.dp, Color(0xFFE5E7EB))
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: system icon + text
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color(0xFF6B7280)
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Row {
                        Text("System Name : ", fontSize = 11.sp, color = Color(0xFF374151))
                        Text("[C0001]", fontSize = 11.sp, color = Color(0xFFEA580C), fontWeight = FontWeight.Bold)
                    }
                    Text(
                        "[Contact Invigilator if the Name and Photograph displayed on the screen is not yours]",
                        fontSize = 10.sp,
                        color = Color(0xFFEA580C),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Right: candidate photo box + info
            Row(
                modifier = Modifier
                    .border(1.dp, Color(0xFF9CA3AF), RoundedCornerShape(2.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, Color(0xFF9CA3AF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, null, Modifier.size(36.dp), Color(0xFF9CA3AF))
                }
                Spacer(Modifier.width(8.dp))
                Column {
                    Row {
                        Text("Candidate Name : ", fontSize = 11.sp, color = Color(0xFF374151))
                        Text("[Your Name]", fontSize = 11.sp, color = Color(0xFFEA580C), fontWeight = FontWeight.Bold)
                    }
                    Row {
                        Text("Subject Name   : ", fontSize = 11.sp, color = Color(0xFF374151))
                        Text("[Practice Paper]", fontSize = 11.sp, color = Color(0xFFEA580C), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // ── Dark blue main area ───────────────────────────────────────────────
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF0B2E66), Color(0xFF1A4A8F), Color(0xFF0B2E66))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // SAMPLE TEXT watermark (faint diagonal, simulated)
            Text(
                text = "SAMPLE TEXT",
                fontSize = 40.sp,
                color = Color.White.copy(alpha = 0.04f),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.Center)
            )

            // ── Login Card ────────────────────────────────────────────────────
            Card(
                modifier = Modifier.width(340.dp),
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        "Login (Demo)",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Username
                    Text("Username", fontSize = 12.sp, color = Color(0xFF374151))
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("223.228.246.62", fontSize = 13.sp, color = Color(0xFF9CA3AF)) },
                        shape = RoundedCornerShape(3.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2563EB),
                            unfocusedBorderColor = Color(0xFFD1D5DB),
                            focusedContainerColor = Color(0xFFF9FAFB),
                            unfocusedContainerColor = Color(0xFFF9FAFB)
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 13.sp)
                    )

                    Spacer(Modifier.height(12.dp))

                    // Password
                    Text("Password", fontSize = 12.sp, color = Color(0xFF374151))
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(3.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2563EB),
                            unfocusedBorderColor = Color(0xFFD1D5DB),
                            focusedContainerColor = Color(0xFFF9FAFB),
                            unfocusedContainerColor = Color(0xFFF9FAFB)
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 13.sp)
                    )

                    Spacer(Modifier.height(16.dp))

                    // LOGIN button
                    Button(
                        onClick = {
                            vm.navigate(Screen.Instructions)
                            vm.startTimer()
                        },
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        shape = RoundedCornerShape(3.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                    ) {
                        Text(
                            "LOGIN",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Click Login To proceed",
                        fontSize = 12.sp,
                        color = Color(0xFFEA580C),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // ── Footer ────────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A3A6B))
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "© All Rights Reserved - National Testing Agency",
                fontSize = 11.sp,
                color = Color.White
            )
        }
    }
}
