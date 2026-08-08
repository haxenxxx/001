package com.ticketchef.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * The app's mascot: a receipt with tiny arms and legs and a friendly face.
 * Increment [captureTrigger] each time the user takes a photo of a ticket —
 * the mascot jumps, the screen flashes like a camera shot, and its eyes turn
 * to hearts for a moment.
 */
@Composable
fun TicketMascot(
    captureTrigger: Int,
    modifier: Modifier = Modifier,
    size: Dp = 140.dp
) {
    val jumpOffset = remember { Animatable(0f) }
    val flashAlpha = remember { Animatable(0f) }
    var showHearts by remember { mutableStateOf(false) }
    var blinkClosed by remember { mutableStateOf(false) }

    LaunchedEffect(captureTrigger) {
        if (captureTrigger <= 0) return@LaunchedEffect
        launch {
            flashAlpha.snapTo(0.9f)
            flashAlpha.animateTo(0f, animationSpec = tween(350))
        }
        launch {
            jumpOffset.animateTo(-38f, animationSpec = tween(140))
            jumpOffset.animateTo(
                0f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
            )
        }
        showHearts = true
        delay(1100)
        showHearts = false
    }

    // Gentle idle blink, purely decorative.
    LaunchedEffect(Unit) {
        while (true) {
            delay(3200)
            blinkClosed = true
            delay(140)
            blinkClosed = false
        }
    }

    val primary = MaterialTheme.colorScheme.primary
    val onSurface = MaterialTheme.colorScheme.onSurface
    val surface = MaterialTheme.colorScheme.surface

    Box(modifier = modifier.size(size + 40.dp), contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .size(size)
                .offset(y = jumpOffset.value.dp)
        ) {
            val w = this.size.width
            val h = this.size.height
            val bodyTop = h * 0.06f
            val bodyBottom = h * 0.72f
            val bodyLeft = w * 0.16f
            val bodyRight = w * 0.84f
            val cornerRadius = w * 0.08f

            // Legs
            drawLine(
                color = onSurface.copy(alpha = 0.85f),
                start = Offset(w * 0.38f, bodyBottom - 2f),
                end = Offset(w * 0.34f, h * 0.94f),
                strokeWidth = w * 0.045f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = onSurface.copy(alpha = 0.85f),
                start = Offset(w * 0.62f, bodyBottom - 2f),
                end = Offset(w * 0.66f, h * 0.94f),
                strokeWidth = w * 0.045f,
                cap = StrokeCap.Round
            )

            // Arms
            drawLine(
                color = onSurface.copy(alpha = 0.85f),
                start = Offset(bodyLeft + 2f, bodyTop + (bodyBottom - bodyTop) * 0.45f),
                end = Offset(w * 0.02f, h * 0.5f),
                strokeWidth = w * 0.04f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = onSurface.copy(alpha = 0.85f),
                start = Offset(bodyRight - 2f, bodyTop + (bodyBottom - bodyTop) * 0.45f),
                end = Offset(w * 0.98f, h * 0.5f),
                strokeWidth = w * 0.04f,
                cap = StrokeCap.Round
            )

            // Ticket body with a jagged bottom edge, like torn receipt paper.
            val body = Path().apply {
                moveTo(bodyLeft, bodyTop + cornerRadius)
                quadraticTo(bodyLeft, bodyTop, bodyLeft + cornerRadius, bodyTop)
                lineTo(bodyRight - cornerRadius, bodyTop)
                quadraticTo(bodyRight, bodyTop, bodyRight, bodyTop + cornerRadius)
                lineTo(bodyRight, bodyBottom)
                val teeth = 6
                val toothWidth = (bodyRight - bodyLeft) / teeth
                for (i in 0 until teeth) {
                    val x1 = bodyRight - i * toothWidth
                    val xMid = x1 - toothWidth / 2
                    val x2 = x1 - toothWidth
                    lineTo(xMid, bodyBottom + toothWidth * 0.35f)
                    lineTo(x2, bodyBottom)
                }
                close()
            }
            drawPath(body, color = surface)
            drawPath(body, color = onSurface.copy(alpha = 0.12f), style = Stroke(width = w * 0.012f))

            // Printed lines on the receipt.
            listOf(0.28f, 0.36f, 0.44f).forEach { fraction ->
                drawLine(
                    color = onSurface.copy(alpha = 0.18f),
                    start = Offset(bodyLeft + w * 0.1f, h * fraction),
                    end = Offset(bodyRight - w * 0.1f, h * fraction),
                    strokeWidth = w * 0.02f,
                    cap = StrokeCap.Round
                )
            }

            // Face
            val eyeY = h * 0.56f
            val eyeDx = w * 0.11f
            val eyeCenterX = w * 0.5f
            if (!showHearts) {
                val eyeHeight = if (blinkClosed) w * 0.01f else w * 0.05f
                drawLine(
                    color = onSurface,
                    start = Offset(eyeCenterX - eyeDx, eyeY - eyeHeight),
                    end = Offset(eyeCenterX - eyeDx, eyeY + eyeHeight),
                    strokeWidth = w * 0.045f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = onSurface,
                    start = Offset(eyeCenterX + eyeDx, eyeY - eyeHeight),
                    end = Offset(eyeCenterX + eyeDx, eyeY + eyeHeight),
                    strokeWidth = w * 0.045f,
                    cap = StrokeCap.Round
                )
            } else {
                drawHeart(Offset(eyeCenterX - eyeDx, eyeY), w * 0.07f, primary)
                drawHeart(Offset(eyeCenterX + eyeDx, eyeY), w * 0.07f, primary)
            }

            // Smile
            val smile = Path().apply {
                moveTo(eyeCenterX - w * 0.1f, h * 0.63f)
                quadraticTo(eyeCenterX, h * 0.7f, eyeCenterX + w * 0.1f, h * 0.63f)
            }
            drawPath(smile, color = onSurface, style = Stroke(width = w * 0.028f, cap = StrokeCap.Round))

            // Cheeks
            drawCircle(primary.copy(alpha = 0.35f), radius = w * 0.045f, center = Offset(eyeCenterX - w * 0.22f, h * 0.6f))
            drawCircle(primary.copy(alpha = 0.35f), radius = w * 0.045f, center = Offset(eyeCenterX + w * 0.22f, h * 0.6f))
        }

        // Camera flash overlay.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = flashAlpha.value))
        )
    }
}

private fun DrawScope.drawHeart(center: Offset, size: Float, color: Color) {
    val path = Path().apply {
        moveTo(center.x, center.y + size * 0.35f)
        cubicTo(
            center.x - size * 1.1f, center.y - size * 0.5f,
            center.x - size * 0.4f, center.y - size * 1.2f,
            center.x, center.y - size * 0.4f
        )
        cubicTo(
            center.x + size * 0.4f, center.y - size * 1.2f,
            center.x + size * 1.1f, center.y - size * 0.5f,
            center.x, center.y + size * 0.35f
        )
        close()
    }
    drawPath(path, color = color)
}
