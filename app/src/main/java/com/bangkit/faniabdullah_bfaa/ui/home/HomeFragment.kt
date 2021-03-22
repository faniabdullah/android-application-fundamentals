package com.bangkit.faniabdullah_bfaa.ui.home

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.faniabdullah_bfaa.R
import com.bangkit.faniabdullah_bfaa.databinding.FragmentHomeBinding
import com.bangkit.faniabdullah_bfaa.domain.model.User
import com.bangkit.faniabdullah_bfaa.ui.adapter.UserAdapter

class HomeFragment : Fragment() {

  companion object {
    private const val STATE_SEARCH = "state_search"
  }

  private var stateSearchView : String ? = null
  private lateinit var searchView: SearchView

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

    if (savedInstanceState != null) {
      stateSearchView = savedInstanceState.getString(STATE_SEARCH);
    }
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    adapter = UserAdapter()
    adapter.notifyDataSetChanged()
    adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
      override fun onItemClicked(data: User) {

      }

    })

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

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.option_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)

    val searchManager = getActivity()?.getSystemService(SEARCH_SERVICE) as SearchManager
    searchView = menu.findItem(R.id.search).actionView as SearchView
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity()?.componentName))
    searchView.queryHint = resources.getString(R.string.search_hint)

    if (stateSearchView != null && !stateSearchView!!.isEmpty() ) {
      menu.findItem(R.id.search).expandActionView()
      searchView.setQuery(stateSearchView,false)
    }

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

  override fun onSaveInstanceState(outState: Bundle) {
    stateSearchView = searchView.query.toString().ifEmpty { null }
    outState.putString(STATE_SEARCH,stateSearchView )
    super.onSaveInstanceState(outState)
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

}