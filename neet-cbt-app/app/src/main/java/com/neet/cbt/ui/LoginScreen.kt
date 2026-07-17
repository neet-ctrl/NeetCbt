package com.neet.cbt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.neet.cbt.ui.theme.*
import com.neet.cbt.viewmodel.ExamViewModel
import com.neet.cbt.viewmodel.Screen

@Composable
fun LoginScreen(vm: ExamViewModel) {
    var username by remember { mutableStateOf("223.228.246.62") }
    var password by remember { mutableStateOf("1234567890") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0B2E66), Color(0xFF1A5EA8), Color(0xFF0B2E66))
                )
            )
    ) {
        // ── Top Header ──────────────────────────────────────────────────────
        Column(modifier = Modifier.fillMaxSize()) {
            NTAHeader()

            // Info bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0F4FF))
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "System Name : [C0001]",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = NTABlue
                    )
                    Text(
                        text = "[Contact Invigilator if the Name and Photograph displayed on the screen is not yours]",
                        fontSize = 10.sp,
                        color = Color(0xFFDC2626),
                        fontWeight = FontWeight.Medium
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Candidate Name : [Your Name]",
                        fontSize = 12.sp,
                        color = NTABlue,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Subject Name : [Practice Paper]",
                        fontSize = 12.sp,
                        color = NTABlue
                    )
                }
            }

            // ── Main Content ─────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Login Card
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .width(340.dp)
                        .wrapContentHeight()
                ) {
                    Column(
                        modifier = Modifier.padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Login (Demo)",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F2937)
                        )

                        // Username
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text("Username", fontSize = 12.sp, color = Color(0xFF6B7280))
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = username,
                                onValueChange = { username = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(4.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = NTALightBlue,
                                    unfocusedBorderColor = Color(0xFFD1D5DB),
                                    focusedContainerColor = Color(0xFFF9FAFB),
                                    unfocusedContainerColor = Color(0xFFF9FAFB)
                                ),
                                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
                            )
                        }

                        // Password
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text("Password", fontSize = 12.sp, color = Color(0xFF6B7280))
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation(),
                                shape = RoundedCornerShape(4.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = NTALightBlue,
                                    unfocusedBorderColor = Color(0xFFD1D5DB),
                                    focusedContainerColor = Color(0xFFF9FAFB),
                                    unfocusedContainerColor = Color(0xFFF9FAFB)
                                ),
                                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
                            )
                        }

                        // LOGIN Button
                        Button(
                            onClick = { vm.navigate(Screen.Instructions) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NTALightBlue
                            )
                        ) {
                            Text(
                                "LOGIN",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        Text(
                            text = "Click Login To proceed",
                            fontSize = 12.sp,
                            color = NTALightBlue,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // ── Footer ───────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A3A6B))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "© All Rights Reserved - National Testing Agency",
                    color = Color.White,
                    fontSize = 11.sp
                )
            }
        }
    }
}
