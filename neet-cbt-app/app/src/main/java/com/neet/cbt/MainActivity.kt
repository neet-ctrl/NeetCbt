package com.neet.cbt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neet.cbt.ui.*
import com.neet.cbt.ui.theme.NEETcbtTheme
import com.neet.cbt.viewmodel.ExamViewModel
import com.neet.cbt.viewmodel.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NEETcbtTheme {
                val vm: ExamViewModel = viewModel()
                val screen by vm.screen.collectAsState()

                when (screen) {
                    is Screen.Login -> LoginScreen(vm = vm)
                    is Screen.Instructions -> InstructionScreen(vm = vm)
                    is Screen.Exam -> ExamScreen(vm = vm)
                    is Screen.Summary -> SummaryScreen(vm = vm)
                    is Screen.Result -> ResultScreen(vm = vm)
                    else -> LoginScreen(vm = vm)
                }
            }
        }
    }
}
