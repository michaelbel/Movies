package org.michaelbel.movies.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.ktx.context
import org.michaelbel.movies.ui.theme.model.ComposeTheme

/**
 * Movies theme.
 *
 * @param theme
 * @param dynamicColors
 * @param content
 */
@Composable
fun MoviesTheme(
    theme: AppTheme = AppTheme.FollowSystem,
    dynamicColors: Boolean = false,
    content: @Composable () -> Unit
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    val dynamicColorsAvailable: Boolean = Build.VERSION.SDK_INT >= 31

    val composeTheme: ComposeTheme = when (theme) {
        AppTheme.NightNo -> {
            ComposeTheme(
                colorScheme = if (dynamicColorsAvailable && dynamicColors) {
                    dynamicLightColorScheme(context)
                } else {
                    LightColorScheme
                },
                statusBarDarkContentEnabled = true
            )
        }
        AppTheme.NightYes -> {
            ComposeTheme(
                colorScheme = if (dynamicColorsAvailable && dynamicColors) {
                    dynamicDarkColorScheme(context)
                } else {
                    DarkColorScheme
                },
                statusBarDarkContentEnabled = false
            )
        }
        AppTheme.FollowSystem -> {
            val darkTheme: Boolean = isSystemInDarkTheme()

            ComposeTheme(
                colorScheme = if (dynamicColorsAvailable && dynamicColors) {
                    if (darkTheme) {
                        dynamicDarkColorScheme(context)
                    } else {
                        dynamicLightColorScheme(context)
                    }
                } else {
                    if (darkTheme) {
                        DarkColorScheme
                    } else {
                        LightColorScheme
                    }
                },
                statusBarDarkContentEnabled = !darkTheme
            )
        }
    }

    val (colorScheme, statusBarDarkContentEnabled) = composeTheme

    systemUiController.statusBarDarkContentEnabled = statusBarDarkContentEnabled

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = MoviesShapes,
        typography = MoviesTypography,
        content = content
    )
}