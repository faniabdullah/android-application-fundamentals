package com.bangkit.faniabdullah_bfaa.ui.home

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.faniabdullah_bfaa.R
import com.bangkit.faniabdullah_bfaa.databinding.FragmentHomeBinding
import com.bangkit.faniabdullah_bfaa.ui.adapter.UserAdapter

class HomeFragment : Fragment() {

  private lateinit var homeViewModel: HomeViewModel

  private lateinit var adapter: UserAdapter
  private var _binding: FragmentHomeBinding ? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    adapter = UserAdapter()
    adapter.notifyDataSetChanged()

    homeViewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)
   
    binding.apply {
      rvUser.layoutManager = LinearLayoutManager(activity)
      rvUser.setHasFixedSize(true)
      rvUser.adapter = adapter
      
    }

    homeViewModel.getSearchUser().observe(viewLifecycleOwner, {
      if (it != null) {
        adapter.setList(it)
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


  private fun searchUser(valueSearch: String){
    binding.apply {
      val query = valueSearch
      if (query.isEmpty()) return
      showLoading(true)
      homeViewModel.setSearchUsers(query)
    }
  }


  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.option_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)

    val searchManager = getActivity()?.getSystemService(SEARCH_SERVICE) as SearchManager
    val searchView = menu.findItem(R.id.search).actionView as SearchView

    searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity()?.componentName))
    searchView.queryHint = resources.getString(R.string.search_hint)

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

      override fun onQueryTextSubmit(query: String): Boolean {
        searchUser(query)
        return true
      }

      override fun onQueryTextChange(newText: String): Boolean {
        return false
      }
    })
  }


  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.search -> {
        return true
      }else -> return true
    }
  }

}