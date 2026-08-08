package com.ticketchef.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Coral,
    onPrimary = Color.White,
    primaryContainer = CoralLight,
    onPrimaryContainer = CoralDark,
    secondary = StoneLight,
    background = Cream,
    onBackground = Charcoal,
    surface = CreamElevated,
    onSurface = Charcoal,
    surfaceVariant = Color(0xFFF0EAE6),
    onSurfaceVariant = StoneLight,
    outline = Color(0xFFD9CFC9)
)

private val DarkColors = darkColorScheme(
    primary = Coral,
    onPrimary = Color.White,
    primaryContainer = CoralDark,
    onPrimaryContainer = CoralLight,
    secondary = StoneDark,
    background = Charcoal,
    onBackground = Cream,
    surface = CharcoalElevated,
    onSurface = Cream,
    surfaceVariant = Color(0xFF3A322F),
    onSurfaceVariant = StoneDark,
    outline = Color(0xFF4A413D)
)

@Composable
fun TicketChefTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = TicketChefTypography,
        content = content
    )
}
