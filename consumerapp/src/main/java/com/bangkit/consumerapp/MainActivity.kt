package com.bangkit.consumerapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.consumerapp.adapter.UserAdapter
import com.bangkit.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.adapter = userAdapter
        }
        viewModel.setFavoriteUser(this)
        viewModel.getFavoriteUser().observe(this, { data ->
            if (data != null) {

                if (data.size == 0) {
                    binding.rvUser.visibility = View.GONE
                    binding.emptyLayout.pictureMsg.visibility = View.VISIBLE
                    binding.emptyLayout.message.visibility = View.VISIBLE
                    binding.emptyLayout.message.text =
                        getString(R.string.notification_empyty_favorite)
                } else {
                    binding.rvUser.visibility = View.VISIBLE
                    binding.emptyLayout.pictureMsg.visibility = View.GONE
                    binding.emptyLayout.message.visibility = View.GONE
                    userAdapter.setList(data)
                }
                showLoading(false)
                showLoading(false)
            }
        })

        showLoading(true)

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.rvUser.visibility = View.GONE
            binding.emptyLayout.message.visibility = View.GONE
            binding.emptyLayout.pictureMsg.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}