package com.gigpilot.app.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class ThemeMode { AUTO, LIGHT, DARK }

enum class NavApp { MAPS, WAZE }

data class AppSettings(
    val themeMode: ThemeMode = ThemeMode.AUTO,
    val alertLeadMinutes: Int = 40,
    val navApp: NavApp = NavApp.MAPS,
    val dailyGoalCents: Long = 20000L
)

class SettingsStore(context: Context) {

    private val prefs =
        context.getSharedPreferences("gigpilot_settings", Context.MODE_PRIVATE)

    private val _settings = MutableStateFlow(load())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    private fun load(): AppSettings = AppSettings(
        themeMode = runCatching {
            ThemeMode.valueOf(prefs.getString("theme_mode", ThemeMode.AUTO.name)!!)
        }.getOrDefault(ThemeMode.AUTO),
        alertLeadMinutes = prefs.getInt("alert_lead", 40),
        navApp = runCatching {
            NavApp.valueOf(prefs.getString("nav_app", NavApp.MAPS.name)!!)
        }.getOrDefault(NavApp.MAPS),
        dailyGoalCents = prefs.getLong("daily_goal_cents", 20000L)
    )

    private fun save(updated: AppSettings) {
        prefs.edit()
            .putString("theme_mode", updated.themeMode.name)
            .putInt("alert_lead", updated.alertLeadMinutes)
            .putString("nav_app", updated.navApp.name)
            .putLong("daily_goal_cents", updated.dailyGoalCents)
            .apply()
        _settings.value = updated
    }

    fun setThemeMode(mode: ThemeMode) = save(_settings.value.copy(themeMode = mode))

    fun setAlertLead(minutes: Int) = save(_settings.value.copy(alertLeadMinutes = minutes))

    fun setNavApp(navApp: NavApp) = save(_settings.value.copy(navApp = navApp))
}
