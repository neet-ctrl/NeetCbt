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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NEETcbtTheme {
                val vm: ExamViewModel = viewModel()
                val screen by vm.screen.collectAsState()

                when (screen) {
                    is Screen.Login        -> LoginScreen(vm = vm)
                    is Screen.Instructions -> InstructionScreen(vm = vm)
                    is Screen.Exam         -> ExamScreen(vm = vm)
                    is Screen.Summary      -> SummaryScreen(vm = vm)
                    is Screen.Result       -> ResultScreen(vm = vm)
                }

                // Copy the bundled PDF from assets to the cache directory once,
                // then hand the file to the ViewModel for page rendering.
                copyPdfAndSetViewModel(vm)
            }
        }
    }

    /**
     * Copies neet_paper.pdf from assets to the app's cache directory (if not already there),
     * then calls [ExamViewModel.setPdfFile] so image questions can render correctly.
     * Runs on IO thread; safe to call multiple times (idempotent).
     */
    private fun copyPdfAndSetViewModel(vm: ExamViewModel) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val destFile = File(cacheDir, "neet_paper.pdf")
                if (!destFile.exists() || destFile.length() == 0L) {
                    assets.open("neet_paper.pdf").use { input ->
                        FileOutputStream(destFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                }
                withContext(Dispatchers.Main) {
                    vm.setPdfFile(destFile)
                }
            } catch (e: Exception) {
                // PDF rendering will simply be unavailable; text questions still work fine
                android.util.Log.e("MainActivity", "Failed to copy PDF: ${e.message}")
            }
        }
    }
}
