package com.bangkit.faniabdullah_bfaa.ui.detailuser

import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.faniabdullah_bfaa.R
import com.bangkit.faniabdullah_bfaa.databinding.ActivityDetailUserBinding
import com.bangkit.faniabdullah_bfaa.databinding.DetailUserItemsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var bindingDetailUser : DetailUserItemsBinding

    companion object{
        const val EXTRA_USERNAME = "username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following,
            R.string.repositories
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        bindingDetailUser = binding.contentDetailUser.includeDetailUser
        setContentView(binding.root)
        val args: DetailUserActivityArgs by navArgs()

        setUpSnackBar()
        showDataDetail(args.username)
        setUpViewPager(args.username)

    }

    private fun showDataDetail(username: String) {
        detailUserViewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        detailUserViewModel.setSearchDetailUsers(username)
        detailUserViewModel.getDetailUser().observe(this, {
            if (it != null) { findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = it.name
                binding.apply {
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(toolbarImage)
                }

                bindingDetailUser.apply {
                    tvUsernameDetail.text = it.login
                    tvLocation.text = it.location
                }
            }
        })
    }

    private fun setUpSnackBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("hmm", null).show()
        }
    }

    private fun setUpViewPager(username: String) {
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME , username)
        val sectionsPagerAdapter = SectionsPagerAdapter(this , bundle)
        val viewPager: ViewPager2 =  bindingDetailUser.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = bindingDetailUser.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }
}