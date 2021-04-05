package com.bangkit.faniabdullah_bfaa.ui.detailuser.following

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.faniabdullah_bfaa.R
import com.bangkit.faniabdullah_bfaa.databinding.FragmentFollowingBinding
import com.bangkit.faniabdullah_bfaa.domain.model.User
import com.bangkit.faniabdullah_bfaa.ui.adapter.UserAdapter
import com.bangkit.faniabdullah_bfaa.ui.detailuser.DetailUserActivity
import com.google.android.material.snackbar.Snackbar

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding as FragmentFollowingBinding
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        binding.apply {
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.setHasFixedSize(true)
            rvFollowing.adapter = userAdapter
        }

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showDetailUser(data)
            }
        })

        userAdapter.setOnItemFavoriteClickCallback(object :
            UserAdapter.OnItemFavoriteClickCallback {

            override fun onItemFavoriteClicked(data: User, stateToogle: ToggleButton) {
                setToggleFavorite(data, stateToogle)
            }
        })


        followingViewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
        followingViewModel.setListFollowing(username)
        followingViewModel.getFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.size == 0) {
                    binding.emptyLayout.message.visibility = View.VISIBLE
                    binding.emptyLayout.message.text =
                        getString(R.string.notification_empyty_following)
                    binding.emptyLayout.pictureMsg.visibility = View.VISIBLE
                    binding.rvFollowing.visibility = View.GONE
                } else {
                    binding.emptyLayout.message.visibility = View.GONE
                    binding.emptyLayout.pictureMsg.visibility = View.GONE
                    binding.rvFollowing.visibility = View.VISIBLE
                    userAdapter.setList(it)
                }
                userAdapter.setList(it)
                showLoading(false)
            }
        })

        showLoading(true)
    }

    private fun setToggleFavorite(data: User, stateToogle: ToggleButton) {
        if (!stateToogle.isChecked) {
            followingViewModel.removeFavoriteUser(data.id)
            Snackbar.make(binding.root,
                R.string.notification_delete_from_favorite,
                Snackbar.LENGTH_LONG).show()
        } else {
            followingViewModel.addToFavorite(data)
            Snackbar.make(binding.root, R.string.notification_add_to_favorite, Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun showDetailUser(data: User) {
        val intentDetail = Intent(context, DetailUserActivity::class.java)
        intentDetail.putExtra(DetailUserActivity.EXTRA_USERNAME_RESULT, data.login)
        startActivity(intentDetail)
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