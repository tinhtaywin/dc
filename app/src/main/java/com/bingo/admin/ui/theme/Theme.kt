package com.bingo.admin.ui.theme

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Neon Color Palette
val NeonCyan = androidx.compose.ui.graphics.Color(0xFF00FFFF)
val NeonPink = androidx.compose.ui.graphics.Color(0xFFFF00FF)
val NeonGreen = androidx.compose.ui.graphics.Color(0xFF00FF00)
val DarkBg = androidx.compose.ui.graphics.Color(0xFF1A1A1A)
val NeonBlue = androidx.compose.ui.graphics.Color(0xFF0000FF)
val NeonOrange = androidx.compose.ui.graphics.Color(0xFFFF4500)
val NeonYellow = androidx.compose.ui.graphics.Color(0xFFFFFF00)

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    secondary = NeonPink,
    tertiary = NeonGreen
)

private val LightColorScheme = lightColorScheme(
    primary = NeonCyan,
    secondary = NeonPink,
    tertiary = NeonGreen
)

@Composable
fun BingoAdminTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Typography
val Typography = androidx.compose.material3.Typography(
    displayLarge = androidx.compose.material3.Typography().displayLarge.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
    ),
    headlineLarge = androidx.compose.material3.Typography().headlineLarge.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
    ),
    titleLarge = androidx.compose.material3.Typography().titleLarge.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
    )
)
