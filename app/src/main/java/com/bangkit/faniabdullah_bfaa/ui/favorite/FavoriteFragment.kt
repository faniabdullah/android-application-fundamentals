package com.bangkit.faniabdullah_bfaa.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.faniabdullah_bfaa.R
import com.bangkit.faniabdullah_bfaa.data.local.FavoriteUser
import com.bangkit.faniabdullah_bfaa.databinding.FragmentFavoriteBinding
import com.bangkit.faniabdullah_bfaa.domain.model.User
import com.bangkit.faniabdullah_bfaa.ui.adapter.UserAdapter
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {

  private var _binding: FragmentFavoriteBinding? = null
  private val binding get() = _binding!!
  private lateinit var favoriteViewModel: FavoriteViewModel

  private lateinit var adapter: UserAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
    _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    adapter = UserAdapter()
    adapter.notifyDataSetChanged()
    adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
      override fun onItemClicked(data: User) {
        showDetailUser(view , data)
      }
    })

    adapter.setOnItemFavoriteClickCallback(object : UserAdapter.OnItemFavoriteClickCallback{

      override fun onItemFavoriteClicked(data: User, stateToogle: ToggleButton) {
        setToogleFavorite(data , stateToogle)
      }
    })

    binding.apply {
      rvUser.layoutManager = LinearLayoutManager(activity)
      rvUser.setHasFixedSize(true)
      rvUser.adapter = adapter
    }

    favoriteViewModel= ViewModelProvider(this).get(FavoriteViewModel::class.java)

    favoriteViewModel.getFavoriteUser()?.observe(viewLifecycleOwner, {
      if (it != null) {
        val list  = mapList(it)
        if (list.size == 0){
          binding.emptyLayout.message.visibility = View.VISIBLE
          binding.emptyLayout.message.text = getString(R.string.notification_empyty_favorite)
          binding.emptyLayout.pictureMsg.visibility = View.VISIBLE
          binding.rvUser.visibility = View.GONE
        }else{
          binding.emptyLayout.message.visibility = View.GONE
          binding.emptyLayout.pictureMsg.visibility = View.GONE
          binding.rvUser.visibility = View.VISIBLE
          adapter.setList(list)
        }
        showLoading(false)
      }
    })

    showLoading(true)

  }

  private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
   val listUser = ArrayList<User>()
    for (user in users){
      val userMapped = User(
        user.login,
        user.id,
        user.avatar_url,
        user.type,
        true,
      )
      listUser.add(userMapped)
    }
    return listUser
  }

  private fun setToogleFavorite(data: User, stateToogle: ToggleButton) {
    if (!stateToogle.isChecked){
      favoriteViewModel.removeFavoriteUser(data.id)
      Snackbar.make(binding.root,R.string.notification_delete_from_favorite, Snackbar.LENGTH_LONG).show()
    }else{
      favoriteViewModel.removeFavoriteUser(data.id)
      Snackbar.make(binding.root,R.string.notification_add_to_favorite, Snackbar.LENGTH_LONG).show()
    }
  }

  private fun showDetailUser(view: View, data: User) {
    val toDetailUserActivity = FavoriteFragmentDirections.actionNavigationFavoriteToDetailUserActivity()
    toDetailUserActivity.username = data.login
    view.findNavController().navigate(toDetailUserActivity)
  }

  private fun showLoading(state: Boolean) {
    if (state) {
      binding.progressBar.visibility = View.VISIBLE
    } else {
      binding.progressBar.visibility = View.GONE
    }
  }


}