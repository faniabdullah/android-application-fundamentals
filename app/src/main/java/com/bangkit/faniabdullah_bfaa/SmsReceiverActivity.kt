package com.bangkit.faniabdullah_bfaa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.faniabdullah_bfaa.databinding.ActivitySmsReceiverBinding

class SmsReceiverActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SMS_NO = "extra_sms_no"
        const val EXTRA_SMS_MESSAGE = "extra_sms_message"
    }
    private var binding: ActivitySmsReceiverBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_receiver)

        binding = ActivitySmsReceiverBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        title = getString(R.string.incoming_message)
        binding?.btnClose?.setOnClickListener {
            finish()
        }
        val senderNo = intent.getStringExtra(EXTRA_SMS_NO)
        val senderMessage = intent.getStringExtra(EXTRA_SMS_MESSAGE)
        binding?.tvFrom?.text = getString(R.string.from, senderNo)
        binding?.tvMessage?.text = senderMessage
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}