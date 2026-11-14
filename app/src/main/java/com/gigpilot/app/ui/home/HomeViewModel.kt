package com.gigpilot.app.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gigpilot.app.data.AppDatabase
import com.gigpilot.app.data.SettingsStore
import com.gigpilot.app.data.Shift
import com.gigpilot.app.util.Format
import com.gigpilot.app.widget.GigPilotWidget
import java.time.LocalDate
import java.time.ZoneId
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HomeUiState(
    val activeShift: Shift? = null,
    val activeElapsedMs: Long = 0,
    val todayTotalCents: Long = 0,
    val netPerHourCents: Long = 0,
    val goalCents: Long = 20000,
    val goalPacePercent: Int = 0
)

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.get(app).shiftDao()
    val settingsStore = SettingsStore(app)

    private val zone: ZoneId = ZoneId.systemDefault()
    private val today: LocalDate = LocalDate.now(zone)
    private val dayStart: Long = today.atStartOfDay(zone).toInstant().toEpochMilli()
    private val dayEnd: Long = today.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli()

    private val nowMs = MutableStateFlow(System.currentTimeMillis())

    private val activeShift: StateFlow<Shift?> = dao.observeActive()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private val todayShifts: StateFlow<List<Shift>> = dao.observeForDay(dayStart, dayEnd)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val uiState: StateFlow<HomeUiState> = combine(
        activeShift,
        todayShifts,
        nowMs,
        settingsStore.settings
    ) { active, shifts, now, settings ->
        var workedMs = 0L
        var totalCents = 0L
        for (shift in shifts) {
            val end = if (shift.endMs > 0) shift.endMs else now
            workedMs += (end - shift.startMs).coerceAtLeast(0)
            totalCents += shift.earningsCents
        }
        val netPerHour = if (workedMs > 0) totalCents * 3_600_000L / workedMs else 0L
        val pace = if (settings.dailyGoalCents > 0) {
            (totalCents * 100 / settings.dailyGoalCents).toInt().coerceIn(0, 100)
        } else {
            0
        }
        HomeUiState(
            activeShift = active,
            activeElapsedMs = active?.let { (now - it.startMs).coerceAtLeast(0) } ?: 0,
            todayTotalCents = totalCents,
            netPerHourCents = netPerHour,
            goalCents = settings.dailyGoalCents,
            goalPacePercent = pace
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState())

    private val _reportShiftId = MutableStateFlow<Long?>(null)
    val reportShiftId: StateFlow<Long?> = _reportShiftId

    init {
        // Per-second tick drives the shift timer and the live net $/hr.
        viewModelScope.launch {
            while (true) {
                nowMs.value = System.currentTimeMillis()
                delay(1_000)
            }
        }
        // Keep the home-screen widget's cached numbers fresh.
        viewModelScope.launch {
            uiState
                .map { Format.money(it.netPerHourCents) }
                .distinctUntilChanged()
                .collect { netText ->
                    val context = getApplication<Application>()
                    context.getSharedPreferences("widget_cache", Application.MODE_PRIVATE)
                        .edit()
                        .putString("net_text", netText)
                        .apply()
                    GigPilotWidget.refresh(context)
                }
        }
    }

    fun startShift() {
        viewModelScope.launch {
            dao.insert(Shift(startMs = System.currentTimeMillis()))
            GigPilotWidget.refresh(getApplication())
        }
    }

    fun endShift() {
        viewModelScope.launch {
            val active = activeShift.value ?: return@launch
            val ended = active.copy(endMs = System.currentTimeMillis())
            dao.update(ended)
            _reportShiftId.value = ended.id
            GigPilotWidget.refresh(getApplication())
        }
    }

    fun reportConsumed() {
        _reportShiftId.value = null
    }

    suspend fun getShift(id: Long): Shift? = dao.getById(id)
}
