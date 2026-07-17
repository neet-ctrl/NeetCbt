package com.neet.cbt.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neet.cbt.viewmodel.ExamViewModel

/**
 * Two-row bottom action bar matching NTA CBT screenshots exactly:
 *
 * Row 1: [SAVE & NEXT]  [CLEAR]  [SAVE & MARK FOR REVIEW]  [MARK FOR REVIEW & NEXT]
 * Row 2: [<< BACK]  [NEXT >>]  ···  [SUBMIT]
 */
@Composable
fun BottomActionBar(
    vm: ExamViewModel,
    onSubmitClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // ── Row 1: main action buttons ────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // SAVE & NEXT
            NtaButton(
                text = "SAVE & NEXT",
                containerColor = Color(0xFF16A34A),
                textColor = Color.White,
                onClick = { vm.saveAndNext() },
                modifier = Modifier.weight(1f)
            )
            // CLEAR
            NtaOutlineButton(
                text = "CLEAR",
                onClick = { vm.clearAnswer() },
                modifier = Modifier.weight(0.6f)
            )
            // SAVE & MARK FOR REVIEW
            NtaButton(
                text = "SAVE & MARK FOR REVIEW",
                containerColor = Color(0xFFF59E0B),
                textColor = Color.White,
                onClick = { vm.saveAndMarkForReview() },
                modifier = Modifier.weight(1.3f)
            )
            // MARK FOR REVIEW & NEXT
            NtaButton(
                text = "MARK FOR REVIEW & NEXT",
                containerColor = Color(0xFF2563EB),
                textColor = Color.White,
                onClick = { vm.markForReviewAndNext() },
                modifier = Modifier.weight(1.3f)
            )
        }

        // ── Row 2: nav + submit ───────────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NtaOutlineButton(
                text = "<< BACK",
                onClick = { vm.goBack() },
                modifier = Modifier.wrapContentWidth()
            )
            Spacer(Modifier.width(6.dp))
            NtaOutlineButton(
                text = "NEXT >>",
                onClick = { vm.goNext() },
                modifier = Modifier.wrapContentWidth()
            )
            Spacer(Modifier.weight(1f))
            NtaButton(
                text = "SUBMIT",
                containerColor = Color(0xFF16A34A),
                textColor = Color.White,
                onClick = onSubmitClick,
                modifier = Modifier.wrapContentWidth()
            )
        }
    }
}

// ─── Private button helpers ───────────────────────────────────────────────────

@Composable
private fun NtaButton(
    text: String,
    containerColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(36.dp),
        shape = RoundedCornerShape(3.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            letterSpacing = 0.2.sp
        )
    }
}

@Composable
private fun NtaOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(36.dp),
        shape = RoundedCornerShape(3.dp),
        border = ButtonDefaults.outlinedButtonBorder,
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF374151)
        )
    }
}
