package com.bangkit.faniabdullah_bfaa.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bangkit.faniabdullah_bfaa.R
import com.bangkit.faniabdullah_bfaa.databinding.ActivitySettingBinding
import com.bangkit.faniabdullah_bfaa.domain.model.Reminder
import com.bangkit.faniabdullah_bfaa.utils.preference.ReminderPreference
import com.bangkit.faniabdullah_bfaa.utils.receiver.AlarmReceiver

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var reminder : Reminder
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.menu_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val reminderPreference = ReminderPreference(this)

        if (reminderPreference.getReminder().isReminded){
            binding.settingSwitchReminder.isChecked = true
            Log.e("but state ","checked")
        }

        alarmReceiver = AlarmReceiver()

        binding.settingSwitchReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                saveReminder(true)
                alarmReceiver.setRepeatingAlarms(this, "RepeatingAlarm","20:04", "Github Reminder")
            }else{
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(state: Boolean) {
        val reminderPreference = ReminderPreference(this)
        reminder = Reminder()
        reminder.isReminded = state
        reminderPreference.setReminder(reminder)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}