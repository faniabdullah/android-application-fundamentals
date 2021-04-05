package com.bangkit.faniabdullah_bfaa.utils.preference

import android.content.Context
import com.bangkit.faniabdullah_bfaa.domain.model.Reminder

class ReminderPreference(context: Context) {
    companion object {
        const val PREFS_NAME = "reminder_prefs"
        private const val REMINDER = "isReminded"
    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: Reminder) {
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value.isReminded)
        editor.apply()
    }

    fun getReminder(): Reminder {
        val model = Reminder()
        model.isReminded = preference.getBoolean(REMINDER, false)
        return model
    }
}