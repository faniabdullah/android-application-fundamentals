package com.bangkit.faniabdullah_bfaa.ui.detailuser.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.faniabdullah_bfaa.databinding.FragmentFollowingBinding
import com.bangkit.faniabdullah_bfaa.ui.adapter.UserAdapter
import com.bangkit.faniabdullah_bfaa.ui.detailuser.DetailUserActivity

class FollowingFragment : Fragment() {
    private var _binding : FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel : FollowingViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var username : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        binding.apply {
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.setHasFixedSize(true)
            rvFollowers.adapter = userAdapter
        }

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        followingViewModel.setListFollowing(username)
        followingViewModel.getFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                userAdapter.setList(it)
                showLoading(false)
            }
        })

        showLoading(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}