package com.bangkit.faniabdullah_bfaa.ui.detailuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.faniabdullah_bfaa.ui.detailuser.followers.FollowersFragment
import com.bangkit.faniabdullah_bfaa.ui.detailuser.following.FollowingFragment
import com.bangkit.faniabdullah_bfaa.ui.detailuser.repositories.RepositoriesFragment

class SectionsPagerAdapter(activity: AppCompatActivity , data : Bundle) : FragmentStateAdapter(activity) {

    private var fragmentBundle : Bundle
    init {
        fragmentBundle = data
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
            2 -> fragment = RepositoriesFragment()
        }
        fragment?.arguments = this.fragmentBundle
        
        return fragment as Fragment
    }
}