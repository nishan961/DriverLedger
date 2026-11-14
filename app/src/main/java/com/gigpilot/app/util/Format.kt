package com.gigpilot.app.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Format {

    fun money(cents: Long): String =
        String.format(Locale.US, "$%.2f", cents / 100.0)

    fun duration(ms: Long): String {
        val totalSeconds = (ms / 1000).coerceAtLeast(0)
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
    }

    fun dateTime(ms: Long): String =
        SimpleDateFormat("MMM d, h:mm a", Locale.US).format(Date(ms))
}
