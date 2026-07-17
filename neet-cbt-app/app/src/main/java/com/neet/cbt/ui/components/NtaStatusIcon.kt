package com.neet.cbt.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Grey outlined square — "Not Visited" */
@Composable
fun NotVisitedIcon(size: Dp = 22.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .border(1.dp, Color(0xFF9CA3AF))
    )
}

/** Red right-pointing pentagon/flag — "Not Answered" */
@Composable
fun NotAnsweredIcon(size: Dp = 22.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(w * 0.70f, 0f)
            lineTo(w, h / 2f)
            lineTo(w * 0.70f, h)
            lineTo(0f, h)
            close()
        }
        drawPath(path, Color(0xFFEF4444))
    }
}

/** Green right-pointing pentagon/flag — "Answered" */
@Composable
fun AnsweredIcon(size: Dp = 22.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(w * 0.70f, 0f)
            lineTo(w, h / 2f)
            lineTo(w * 0.70f, h)
            lineTo(0f, h)
            close()
        }
        drawPath(path, Color(0xFF16A34A))
    }
}

/** Purple filled circle — "Marked for Review" */
@Composable
fun MarkedIcon(size: Dp = 22.dp) {
    Canvas(modifier = Modifier.size(size)) {
        drawCircle(Color(0xFF7C3AED))
    }
}

/** Purple circle with green dot (bottom-right) — "Answered & Marked for Review" */
@Composable
fun AnsweredMarkedIcon(size: Dp = 22.dp) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        // Purple base circle
        drawCircle(Color(0xFF7C3AED))
        // Green dot bottom-right
        drawCircle(
            color = Color(0xFF16A34A),
            radius = w * 0.28f,
            center = Offset(w * 0.72f, h * 0.72f)
        )
    }
}
