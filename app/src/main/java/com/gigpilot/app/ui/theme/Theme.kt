package com.gigpilot.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.gigpilot.app.data.ThemeMode
import com.gigpilot.app.util.Solar
import java.time.ZonedDateTime
import kotlinx.coroutines.delay

private val LightColors = lightColorScheme(
    primary = Navy,
    onPrimary = Color.White,
    secondary = Teal,
    onSecondary = Color.White,
    tertiary = Amber,
    background = Color.White,
    onBackground = Navy,
    surface = Color(0xFFF2F5F7),
    onSurface = Navy
)

private val DarkColors = darkColorScheme(
    primary = Amber,
    onPrimary = Color.Black,
    secondary = Teal,
    onSecondary = Color.White,
    tertiary = Amber,
    background = TrueBlack,
    onBackground = Color.White,
    surface = NightSurface,
    onSurface = Color.White
)

/**
 * GigPilot theme. In AUTO mode the white daytime UI and the true-black night UI
 * switch by local sunrise / sunset (Vancouver's sunset ranges ~4:15 PM in winter
 * to ~9:20 PM in summer, so a fixed clock would be wrong). Layouts are identical
 * in both themes; only colors change.
 */
@Composable
fun GigPilotTheme(
    themeMode: ThemeMode,
    content: @Composable () -> Unit
) {
    var now by remember { mutableStateOf(ZonedDateTime.now()) }
    LaunchedEffect(Unit) {
        while (true) {
            now = ZonedDateTime.now()
            delay(60_000)
        }
    }
    val dark = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.AUTO -> !Solar.isDaytime(now)
    }
    MaterialTheme(
        colorScheme = if (dark) DarkColors else LightColors,
        content = content
    )
}
