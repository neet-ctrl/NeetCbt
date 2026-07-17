package com.neet.cbt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.data.Question
import com.neet.cbt.data.QuestionStatus
import com.neet.cbt.data.Section
import com.neet.cbt.ui.theme.*
import com.neet.cbt.viewmodel.ExamViewModel

@Composable
fun QuestionPalette(vm: ExamViewModel) {
    val paletteVisible by vm.paletteVisible.collectAsState()
    val exam by vm.exam.collectAsState()
    val statuses by vm.statuses.collectAsState()
    val answers by vm.answers.collectAsState()
    val currentSectionIdx by vm.currentSectionIndex.collectAsState()
    val currentQIdx by vm.currentQuestionIndexInSection.collectAsState()

    // Summary counts
    val allQuestions = exam.sections.flatMap { it.questions }
    val notVisited   = allQuestions.count { (statuses[it.id] ?: QuestionStatus.NOT_VISITED) == QuestionStatus.NOT_VISITED }
    val notAnswered  = allQuestions.count { (statuses[it.id] ?: QuestionStatus.NOT_VISITED) == QuestionStatus.NOT_ANSWERED }
    val answered     = allQuestions.count { (statuses[it.id] ?: QuestionStatus.NOT_VISITED) == QuestionStatus.ANSWERED }
    val marked       = allQuestions.count { (statuses[it.id] ?: QuestionStatus.NOT_VISITED) == QuestionStatus.MARKED_FOR_REVIEW }
    val answeredMark = allQuestions.count { (statuses[it.id] ?: QuestionStatus.NOT_VISITED) == QuestionStatus.ANSWERED_AND_MARKED }

    Row(modifier = Modifier.fillMaxHeight()) {
        // Toggle button (arrow on left edge of palette)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(20.dp)
                .background(Color(0xFF374151))
                .clickable { vm.togglePalette() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (paletteVisible) Icons.Default.ChevronRight else Icons.Default.ChevronLeft,
                contentDescription = "Toggle Palette",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }

        if (paletteVisible) {
            Column(
                modifier = Modifier
                    .width(220.dp)
                    .fillMaxHeight()
                    .background(Color.White)
                    .border(1.dp, NTABorder)
            ) {
                // Summary legend
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(NTALightGrey)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PaletteLegendItem(StatusNotVisited, notVisited.toString(), "Not Visited", borderColor = Color.Gray)
                        PaletteLegendItem(StatusNotAnswered, notAnswered.toString(), "Not Answered")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PaletteLegendItem(StatusAnswered, answered.toString(), "Answered")
                        PaletteLegendItem(StatusMarkedForReview, marked.toString(), "Marked for Review")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        PaletteLegendItem(
                            StatusAnsweredAndMarked,
                            answeredMark.toString(),
                            "Ans & Marked",
                            dotColor = StatusAnswered
                        )
                    }
                }

                HorizontalDivider(color = NTABorder)

                // Grid of question numbers
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    exam.sections.forEachIndexed { si, section ->
                        // Section header
                        item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(5) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(NTABlue)
                                    .padding(vertical = 3.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    section.name,
                                    fontSize = 10.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        items(section.questions) { question ->
                            val status = statuses[question.id] ?: QuestionStatus.NOT_VISITED
                            val isCurrentQ = (si == currentSectionIdx &&
                                    section.questions.indexOf(question) == currentQIdx)
                            QuestionCell(
                                question = question,
                                status = status,
                                isSelected = isCurrentQ,
                                onClick = {
                                    val qi = section.questions.indexOf(question)
                                    vm.navigateToQuestion(si, qi)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuestionCell(
    question: Question,
    status: QuestionStatus,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = when (status) {
        QuestionStatus.NOT_VISITED -> StatusNotVisited
        QuestionStatus.NOT_ANSWERED -> StatusNotAnswered
        QuestionStatus.ANSWERED -> StatusAnswered
        QuestionStatus.MARKED_FOR_REVIEW -> StatusMarkedForReview
        QuestionStatus.ANSWERED_AND_MARKED -> StatusAnsweredAndMarked
    }
    val textColor = when (status) {
        QuestionStatus.NOT_VISITED -> Color(0xFF374151)
        else -> Color.White
    }

    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(bgColor)
            .then(
                if (isSelected) Modifier.border(2.dp, Color(0xFFFBBF24), RoundedCornerShape(4.dp))
                else Modifier
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = question.id.toString(),
            color = textColor,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        // Green dot overlay for ANSWERED_AND_MARKED
        if (status == QuestionStatus.ANSWERED_AND_MARKED) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(StatusAnswered)
            )
        }
    }
}

@Composable
private fun PaletteLegendItem(
    color: Color,
    count: String,
    label: String,
    borderColor: Color = Color.Transparent,
    dotColor: Color? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(color)
                .then(
                    if (borderColor != Color.Transparent)
                        Modifier.border(1.dp, borderColor, RoundedCornerShape(3.dp))
                    else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count,
                color = if (color == StatusNotVisited) Color(0xFF374151) else Color.White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
            if (dotColor != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(dotColor)
                )
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, fontSize = 9.sp, color = Color(0xFF374151))
    }
}
