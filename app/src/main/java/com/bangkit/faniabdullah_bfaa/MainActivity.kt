package com.bangkit.faniabdullah_bfaa

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.faniabdullah_bfaa.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.DecimalFormat

class MainActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeatherAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        private const val JOB_ID = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = WeatherAdapter()
        adapter.notifyDataSetChanged()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)


        binding.btnCity.setOnClickListener {
            val city = binding.editCity.text.toString()
            if (city.isEmpty()) return@setOnClickListener
            showLoading(true)
            mainViewModel.setWeather(city)
        }

        mainViewModel.getWeathers().observe(this, { weatherItems ->
            if (weatherItems != null) {
                adapter.setData(weatherItems)
                showLoading(false)
            }
        })

    }



    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}