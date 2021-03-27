package com.bangkit.faniabdullah_bfaa.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.faniabdullah_bfaa.R
import com.bangkit.faniabdullah_bfaa.databinding.ItemRepositoriesBinding
import com.bangkit.faniabdullah_bfaa.domain.model.RepositoriesResponse

class RepositoriesAdapter : RecyclerView.Adapter<RepositoriesAdapter.RepositoriesViewHolder>() {
    private  val list = ArrayList<RepositoriesResponse>()

    inner class RepositoriesViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRepositoriesBinding.bind(itemView)
        fun bind(repos: RepositoriesResponse) {
            binding.apply {
                tvNameRepos.text = repos.name
                tvDescRepos.text = repos.description
                tcStartRepos.text = repos.stargazers_count.toString()
            }
        }
    }

    fun setList(repos : ArrayList<RepositoriesResponse>){
        list.clear()
        list.addAll(repos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_repositories, parent, false)
        return RepositoriesViewHolder(mView)
    }

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return  list.size
    }
}