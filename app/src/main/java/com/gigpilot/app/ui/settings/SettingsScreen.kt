package com.gigpilot.app.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gigpilot.app.R
import com.gigpilot.app.data.NavApp
import com.gigpilot.app.data.SettingsStore
import com.gigpilot.app.data.ThemeMode
import com.gigpilot.app.util.Format

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsStore: SettingsStore,
    onBack: () -> Unit
) {
    val settings by settingsStore.settings.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.report_back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.settings_theme),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(6.dp))
            ThemeOption(
                label = stringResource(R.string.theme_auto),
                selected = settings.themeMode == ThemeMode.AUTO
            ) { settingsStore.setThemeMode(ThemeMode.AUTO) }
            ThemeOption(
                label = stringResource(R.string.theme_light),
                selected = settings.themeMode == ThemeMode.LIGHT
            ) { settingsStore.setThemeMode(ThemeMode.LIGHT) }
            ThemeOption(
                label = stringResource(R.string.theme_dark),
                selected = settings.themeMode == ThemeMode.DARK
            ) { settingsStore.setThemeMode(ThemeMode.DARK) }

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.settings_alert_lead),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
            Row {
                listOf(20, 40, 60).forEach { minutes ->
                    FilterChip(
                        selected = settings.alertLeadMinutes == minutes,
                        onClick = { settingsStore.setAlertLead(minutes) },
                        label = {
                            Text(stringResource(R.string.minutes_format, minutes))
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.settings_nav_app),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
            Row {
                FilterChip(
                    selected = settings.navApp == NavApp.MAPS,
                    onClick = { settingsStore.setNavApp(NavApp.MAPS) },
                    label = { Text(stringResource(R.string.nav_maps)) },
                    modifier = Modifier.padding(end = 8.dp)
                )
                FilterChip(
                    selected = settings.navApp == NavApp.WAZE,
                    onClick = { settingsStore.setNavApp(NavApp.WAZE) },
                    label = { Text(stringResource(R.string.nav_waze)) }
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.settings_daily_goal),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = Format.money(settings.dailyGoalCents),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
private fun ThemeOption(
    label: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onSelect)
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}
