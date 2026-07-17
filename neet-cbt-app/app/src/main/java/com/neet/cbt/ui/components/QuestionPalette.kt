package com.neet.cbt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.data.QuestionStatus
import com.neet.cbt.viewmodel.ExamViewModel

@Composable
fun QuestionPalette(vm: ExamViewModel) {
    val exam              by vm.exam.collectAsState()
    val statuses          by vm.statuses.collectAsState()
    val currentSectionIdx by vm.currentSectionIndex.collectAsState()
    val currentQIdx       by vm.currentQuestionIndexInSection.collectAsState()
    val paletteVisible    by vm.paletteVisible.collectAsState()

    val allQuestions = exam.sections.flatMap { it.questions }
    val currentQuestionId = exam.sections[currentSectionIdx].questions[currentQIdx].id

    // ── Counts for legend ──────────────────────────────────────────────────
    val notVisited  = allQuestions.count { statuses[it.id] == null || statuses[it.id] == QuestionStatus.NOT_VISITED }
    val notAnswered = allQuestions.count { statuses[it.id] == QuestionStatus.NOT_ANSWERED }
    val answered    = allQuestions.count { statuses[it.id] == QuestionStatus.ANSWERED }
    val marked      = allQuestions.count { statuses[it.id] == QuestionStatus.MARKED_FOR_REVIEW }
    val answeredMkd = allQuestions.count { statuses[it.id] == QuestionStatus.ANSWERED_AND_MARKED }

    if (!paletteVisible) {
        // Palette collapsed — just show the expand arrow tab
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(20.dp)
                .background(Color(0xFF374151))
                .clickable { vm.togglePalette() },
            contentAlignment = Alignment.Center
        ) {
            Text("◀", color = Color.White, fontSize = 12.sp)
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(220.dp)
            .background(Color(0xFFF9FAFB))
    ) {
        // ── Header with collapse button ────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF374151))
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Question Palette", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Box(
                modifier = Modifier
                    .clickable { vm.togglePalette() }
                    .padding(4.dp)
            ) {
                Text("▶", color = Color.White, fontSize = 11.sp)
            }
        }

        // ── Status Legend (dashed box) ─────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .border(1.dp, Color(0xFF9CA3AF), RoundedCornerShape(4.dp))
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Row 1: Not Visited | Not Answered
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendItem(count = notVisited, icon = { NotVisitedIcon(18.dp) }, label = "Not Visited")
                LegendItem(count = notAnswered, icon = { NotAnsweredIcon(18.dp) }, label = "Not\nAnswered")
            }
            // Row 2: Answered | Marked for Review
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendItem(count = answered, icon = { AnsweredIcon(18.dp) }, label = "Answered")
                LegendItem(count = marked, icon = { MarkedIcon(18.dp) }, label = "Marked for\nReview")
            }
            // Row 3: Answered & Marked (full width)
            Row(verticalAlignment = Alignment.CenterVertically) {
                AnsweredMarkedIcon(18.dp)
                Spacer(Modifier.width(4.dp))
                Text(
                    "Answered & Marked for Review\n(will be considered for evaluation)",
                    fontSize = 9.sp,
                    color = Color(0xFF374151),
                    lineHeight = 12.sp
                )
            }
        }

        // ── Section tabs ───────────────────────────────────────────────────
        exam.sections.forEachIndexed { si, section ->
            val isActive = si == currentSectionIdx
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (isActive) Color(0xFF1A3A6B) else Color(0xFFE5E7EB))
                    .clickable { vm.navigateToQuestion(si, 0) }
                    .padding(horizontal = 8.dp, vertical = 5.dp)
            ) {
                Text(
                    section.name,
                    fontSize = 11.sp,
                    color = if (isActive) Color.White else Color(0xFF374151),
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        // ── Question grid ─────────────────────────────────────────────────
        val sectionQuestions = exam.sections[currentSectionIdx].questions

        LazyVerticalGrid(
            columns = GridCells.Fixed(8),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 6.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            items(sectionQuestions) { q ->
                val status = statuses[q.id] ?: QuestionStatus.NOT_VISITED
                val isCurrent = q.id == currentQuestionId
                val bgColor = when (status) {
                    QuestionStatus.NOT_VISITED         -> Color(0xFFD1D5DB)
                    QuestionStatus.NOT_ANSWERED        -> Color(0xFFEF4444)
                    QuestionStatus.ANSWERED            -> Color(0xFF16A34A)
                    QuestionStatus.MARKED_FOR_REVIEW   -> Color(0xFF7C3AED)
                    QuestionStatus.ANSWERED_AND_MARKED -> Color(0xFFD97706)
                }
                val textColor = when (status) {
                    QuestionStatus.NOT_VISITED -> Color(0xFF374151)
                    else -> Color.White
                }
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(bgColor, RoundedCornerShape(3.dp))
                        .then(
                            if (isCurrent) Modifier.border(2.dp, Color(0xFF1F2937), RoundedCornerShape(3.dp))
                            else Modifier
                        )
                        .clickable { vm.navigateToQuestionById(q.id) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${q.id}",
                        fontSize = 9.sp,
                        color = textColor,
                        fontWeight = if (isCurrent) FontWeight.ExtraBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
private fun LegendItem(count: Int, icon: @Composable () -> Unit, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Count badge
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(Color(0xFFD1D5DB), RoundedCornerShape(3.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("$count", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
        }
        Spacer(Modifier.width(4.dp))
        icon()
        Spacer(Modifier.width(4.dp))
        Text(label, fontSize = 9.sp, color = Color(0xFF374151), lineHeight = 11.sp)
    }
}
