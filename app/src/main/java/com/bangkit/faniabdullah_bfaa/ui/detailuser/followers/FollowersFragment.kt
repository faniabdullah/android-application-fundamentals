package com.bangkit.faniabdullah_bfaa.ui.detailuser.followers

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
import com.bangkit.faniabdullah_bfaa.databinding.FragmentFollowersBinding
import com.bangkit.faniabdullah_bfaa.domain.model.User
import com.bangkit.faniabdullah_bfaa.ui.adapter.UserAdapter
import com.bangkit.faniabdullah_bfaa.ui.detailuser.DetailUserActivity
import com.google.android.material.snackbar.Snackbar

class FollowersFragment : Fragment(R.layout.fragment_followers) {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var followersViewModel : FollowersViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var username : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        val view = binding.root
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showDetailUser(data)
            }
        })

        userAdapter.setOnItemFavoriteClickCallback(object : UserAdapter.OnItemFavoriteClickCallback{

            override fun onItemFavoriteClicked(data: User, stateToogle: ToggleButton) {
                setToogleFavorite(data , stateToogle)
            }

        })

        binding.apply {
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.setHasFixedSize(true)
            rvFollowers.adapter = userAdapter
        }

        followersViewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)
        followersViewModel.setListFollowers(username)
        followersViewModel.getFollowers().observe(viewLifecycleOwner, {

            if (it != null) {
                if (it.size == 0){
                    binding.emptyLayout.message.visibility = View.VISIBLE
                    binding.emptyLayout.message.text = getString(R.string.notification_empyty_followers)
                    binding.emptyLayout.pictureMsg.visibility = View.VISIBLE
                    binding.rvFollowers.visibility = View.GONE
                }else{
                    binding.emptyLayout.message.visibility = View.GONE
                    binding.emptyLayout.pictureMsg.visibility = View.GONE
                    binding.rvFollowers.visibility = View.VISIBLE
                    userAdapter.setList(it)
                }
                showLoading(false)
            }
        })
        showLoading(true)
    }

    private fun showDetailUser( data: User) {
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

    private fun setToogleFavorite(data: User, stateToogle: ToggleButton) {

        if (!stateToogle.isChecked){
            followersViewModel.removeFavoriteUser(data.id)
            Snackbar.make(binding.root,R.string.notification_delete_from_favorite, Snackbar.LENGTH_LONG).show()
        }else{
            followersViewModel.addToFavorite(data)
            Snackbar.make(binding.root,R.string.notification_add_to_favorite, Snackbar.LENGTH_LONG).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}