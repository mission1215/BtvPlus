package com.skb.btvplus.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import timber.log.Timber

var originalColor: Int = 0
@Composable
fun BtvPlusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    isCustomStatusBarColor: Boolean = false,
    dynamicColor: Boolean = true, content: @Composable () -> Unit,
) {
    val DarkColorScheme = darkColorScheme(
        primary = Gray95,
        secondary = Gray95,
        tertiary = Gray95,
        onSurface = Gray95
    )

    val LightColorScheme = lightColorScheme(
        primary = Gray0White,
        secondary = Gray0White,
        tertiary = Gray0White,
        onSurface = Gray0White
    )

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
        DisposableEffect(Unit) {
            val window = (view.context as Activity).window
            if (isCustomStatusBarColor) {
                originalColor = window.statusBarColor
            }
            window.statusBarColor = if (darkTheme) {
                DarkColorScheme.primary.toArgb() // 다크 모드일 때 검정색
            } else {
                LightColorScheme.primary.toArgb() // 라이트 모드일 때 밝은 색
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            Timber.tag("AosTheme")
                .d("LightColorScheme.primary: ${LightColorScheme.primary.toArgb()}")
            onDispose {
                if (isCustomStatusBarColor) {
                    window.statusBarColor = originalColor
                }
            }
        }
    }


    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}