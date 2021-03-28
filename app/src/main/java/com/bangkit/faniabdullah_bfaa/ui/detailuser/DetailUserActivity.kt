package com.bangkit.faniabdullah_bfaa.ui.detailuser

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.faniabdullah_bfaa.R
import com.bangkit.faniabdullah_bfaa.databinding.ActivityDetailUserBinding
import com.bangkit.faniabdullah_bfaa.databinding.DetailUserItemsBinding
import com.bangkit.faniabdullah_bfaa.domain.model.User
import com.bangkit.faniabdullah_bfaa.ui.home.HomeViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var bindingDetailUser: DetailUserItemsBinding
    private var stateUsername: String? = null

    companion object {
        const val EXTRA_USERNAME = "username"
        const val EXTRA_USERNAME_RESULT = "username_result"

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

        val result: String? = intent.getStringExtra(EXTRA_USERNAME_RESULT)
        stateUsername = result

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val args: DetailUserActivityArgs by navArgs()
        args.username?.let { stateUsername = it }

        stateUsername?.let {
            showDataDetail(it)
            setUpViewPager(it)
        }

    }

    private fun showDataDetail(username: String) {
        var stateFavorite = false
        detailUserViewModel= ViewModelProvider(this).get(DetailUserViewModel::class.java)
        detailUserViewModel.setSearchDetailUsers(username)
        detailUserViewModel.getDetailUser().observe(this, {

            if (it != null) {
                findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = it.name
                val followerCount = it.followers
                val followingCount = it.following
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
                    tvFollowers.text = getString(R.string.followers_users , followerCount)
                    tvFollowing.text = getString(R.string.following_users , followingCount)
                }

                CoroutineScope(Dispatchers.IO).launch {
                    val count = detailUserViewModel.isFavoriteUser(it.id)
                    withContext(Dispatchers.Main){
                        if (count != null){
                            if (count > 0){
                                bindingDetailUser.toogleFavorite.isChecked = true
                                stateFavorite = true
                            }else{
                                bindingDetailUser.toogleFavorite.isChecked = false
                                stateFavorite = false
                            }
                        }
                    }
                }

                val dataUser = User(
                    it.login,
                    it.id,
                    it.avatar_url,
                    it.type,
                    true
                )

                bindingDetailUser.toogleFavorite.setOnClickListener {
                    if (stateFavorite){
                        detailUserViewModel.removeFavoriteUser(dataUser.id)
                        Snackbar.make(binding.root,R.string.notification_delete_from_favorite,
                            Snackbar.LENGTH_LONG).show()
                        stateFavorite = false
                    }else{
                        detailUserViewModel.addToFavorite(dataUser)
                        Snackbar.make(binding.root,R.string.notification_add_to_favorite,Snackbar.LENGTH_LONG).show()
                        stateFavorite = true
                    }
                }
            }

        })

        detailUserViewModel.checkStatusServer().observe(this, {
            if (it != true){
                bindingDetailUser.apply {
                    tvUsernameDetail.visibility = View.GONE
                    tvLocation.visibility = View.GONE
                    tvFollowers.visibility = View.GONE
                    tvFollowing.visibility = View.GONE
                    viewPager.visibility = View.GONE
                    tabLayout.visibility =View.GONE
                    toogleFavorite.visibility = View.GONE
                    emptyLayout.pictureMsg.visibility = View.VISIBLE
                    emptyLayout.message.visibility = View.VISIBLE
                    emptyLayout.message.text = getString(R.string.notification_error_server)
                }
            }
        })
    }

    private fun setUpViewPager(username: String) {
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = bindingDetailUser.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = bindingDetailUser.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}