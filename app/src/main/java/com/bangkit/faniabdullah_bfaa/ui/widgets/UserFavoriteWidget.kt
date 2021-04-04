package com.bangkit.faniabdullah_bfaa.ui.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.bangkit.faniabdullah_bfaa.R
import com.bangkit.faniabdullah_bfaa.ui.MainActivity

class UserFavoriteWidget : AppWidgetProvider() {
    companion object {
        private const val ACTION_REFRESH = "action_refresh"

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
        ) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val refreshIntent = Intent(context, UserFavoriteWidget::class.java)
            refreshIntent.action = ACTION_REFRESH
            refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            val refreshPendingIntent = PendingIntent.getBroadcast(context,
                0,
                refreshIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val toMainActivityIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val toMainPendingIntent = PendingIntent.getActivity(context,
                0,
                toMainActivityIntent,
                0)

            val views = RemoteViews(context.packageName, R.layout.user_favorite_widget)
            views.apply {
                setRemoteAdapter(R.id.stack_view, intent)
                setEmptyView(R.id.stack_view, R.id.empty_view)
                setOnClickPendingIntent(R.id.imageButtonRefreshWidget, refreshPendingIntent)
                setOnClickPendingIntent(R.id.banner_text_widget, toMainPendingIntent)
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.action == ACTION_REFRESH) {
                val component = context?.let { context ->
                    ComponentName(
                        context,
                        UserFavoriteWidget::class.java
                    )
                }
                AppWidgetManager.getInstance(context).apply {
                    notifyAppWidgetViewDataChanged(
                        getAppWidgetIds(component),
                        R.id.stack_view
                    )
                }
            }
        }
        super.onReceive(context, intent)
    }
}

