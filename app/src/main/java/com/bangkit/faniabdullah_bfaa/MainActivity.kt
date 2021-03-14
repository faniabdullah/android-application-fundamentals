package com.bangkit.faniabdullah_bfaa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.faniabdullah_bfaa.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val btnStartService = findViewById<Button>(R.id.btn_start_service)
        btnStartService.setOnClickListener {
            val mStartServiceIntent = Intent(this, MyService::class.java)
            startService(mStartServiceIntent)
        }
        val btnStartJobIntentService = findViewById<Button>(R.id.btn_start_job_intent_service)
        btnStartJobIntentService.setOnClickListener {


        }
        val btnStartBoundService = findViewById<Button>(R.id.btn_start_bound_service)
        btnStartBoundService.setOnClickListener {

        }
        val btnStopBoundService = findViewById<Button>(R.id.btn_stop_bound_service)
        btnStopBoundService.setOnClickListener {

        }
    }
}