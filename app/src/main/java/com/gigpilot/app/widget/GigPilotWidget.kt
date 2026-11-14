package com.gigpilot.app.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.widget.RemoteViews
import com.gigpilot.app.R

/**
 * Home-screen widget: live net $/hr + next alert.
 * Values are cached by the app (widget_cache prefs) and refreshed on change;
 * the provider also refreshes on the system update tick.
 */
class GigPilotWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { id -> updateWidget(context, appWidgetManager, id) }
    }

    companion object {

        fun refresh(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(ComponentName(context, GigPilotWidget::class.java))
            ids.forEach { id -> updateWidget(context, manager, id) }
        }

        private fun updateWidget(context: Context, manager: AppWidgetManager, id: Int) {
            val prefs = context.getSharedPreferences("widget_cache", Context.MODE_PRIVATE)
            val netText = prefs.getString("net_text", null) ?: "$0.00"
            val alertText = prefs.getString("alert_text", null)
                ?: context.getString(R.string.widget_none)
            val views = RemoteViews(context.packageName, R.layout.widget_gigpilot)
            views.setTextViewText(R.id.widget_net_value, netText)
            views.setTextViewText(
                R.id.widget_alert,
                context.getString(R.string.widget_next_alert) + ": " + alertText
            )
            manager.updateAppWidget(id, views)
        }
    }
}
