package com.bangkit.faniabdullah_bfaa.ui.detailuser.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.faniabdullah_bfaa.databinding.FragmentRepositoriesBinding
import com.bangkit.faniabdullah_bfaa.ui.adapter.RepositoriesAdapter
import com.bangkit.faniabdullah_bfaa.ui.detailuser.DetailUserActivity

class RepositoriesFragment : Fragment(){
    private var _binding: FragmentRepositoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var repoAdapter: RepositoriesAdapter
    private lateinit var username : String
    private lateinit var repositoriesViewModel : RepositoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        val view = binding.root
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        repoAdapter = RepositoriesAdapter()
        repoAdapter.notifyDataSetChanged()
        binding.apply {
            rvRepositories.layoutManager = LinearLayoutManager(activity)
            rvRepositories.setHasFixedSize(true)
            rvRepositories.adapter = repoAdapter
        }

        repositoriesViewModel  = ViewModelProvider(this).get(RepositoriesViewModel::class.java)
        repositoriesViewModel.setListRepositories(username)

        repositoriesViewModel.getRepositories().observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.size == 0){
                    binding.emptyLayout.message.visibility = View.VISIBLE
                    binding.emptyLayout.message.text = "Pengguna ini tidak punya Repositories"
                    binding.emptyLayout.pictureMsg.visibility = View.VISIBLE
                    binding.rvRepositories.visibility = View.GONE
                }else{
                    binding.emptyLayout.message.visibility = View.GONE
                    binding.emptyLayout.pictureMsg.visibility = View.GONE
                    binding.rvRepositories.visibility = View.VISIBLE
                    repoAdapter.setList(it)
                }
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
}