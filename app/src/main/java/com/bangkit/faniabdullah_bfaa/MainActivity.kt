package com.bangkit.faniabdullah_bfaa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.faniabdullah_bfaa.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


}