package com.bangkit.faniabdullah_bfaa.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.faniabdullah_bfaa.databinding.ActivityMainBinding
import com.bangkit.faniabdullah_bfaa.ui.adapter.UserAdapter

class MainActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        viewModel= ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter

            btnSearch.setOnClickListener{
                searchUser()
            }

            usernameTv.setOnKeyListener{ v , keyCode,event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                 return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUser().observe(this,{
            if (it!= null){
                adapter.setList(it)
                showLoading(false)
            }
        })

    }

    private fun searchUser(){
        binding.apply {
            val query = usernameTv.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}