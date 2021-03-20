package com.bangkit.faniabdullah_bfaa.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bangkit.faniabdullah_bfaa.R
import com.bangkit.faniabdullah_bfaa.ui.home.HomeViewModel

class SearchFragment : Fragment() {

    private lateinit var homeViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        val textView: TextView = root.findViewById(R.id.text_search)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        Log.e("load","terload")
        return root
    }
}