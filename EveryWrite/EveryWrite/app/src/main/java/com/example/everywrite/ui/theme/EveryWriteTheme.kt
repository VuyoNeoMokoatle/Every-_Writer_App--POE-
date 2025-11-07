package com.example.everywrite.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val EveryWriteLightColorScheme = lightColorScheme(
    primary = Green40,
    onPrimary = Color.White,
    primaryContainer = Green40,
    onPrimaryContainer = Color.White,
    secondary = Green80,
    onSecondary = Color.White,
    tertiary = Green20,
    onTertiary = Color.White,
    background = WarmWhite,
    onBackground = Charcoal,
    surface = Color.White,
    onSurface = Charcoal,
    surfaceVariant = LightGreen,
    onSurfaceVariant = LightCharcoal,
    outline = Green80,
    outlineVariant = Green80.copy(alpha = 0.5f)
)

private val EveryWriteDarkColorScheme = darkColorScheme(
    primary = Green80,
    onPrimary = Charcoal,
    primaryContainer = Green80,
    onPrimaryContainer = Charcoal,
    secondary = Green40,
    onSecondary = Color.White,
    tertiary = LightGreen,
    onTertiary = Charcoal,
    background = Color(0xFF121212),
    onBackground = Color(0xFFE8F5E8),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE8F5E8),
    surfaceVariant = Color(0xFF2D2D2D),
    onSurfaceVariant = Color(0xFFC8E6C9),
    outline = Green40,
    outlineVariant = Green40.copy(alpha = 0.5f)
)

@Composable
fun EveryWriteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> EveryWriteDarkColorScheme
        else -> EveryWriteLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}