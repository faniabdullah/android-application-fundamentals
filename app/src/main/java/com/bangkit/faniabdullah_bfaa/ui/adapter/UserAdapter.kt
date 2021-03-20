package com.bangkit.faniabdullah_bfaa.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.faniabdullah_bfaa.databinding.UserItemsBinding
import com.bangkit.faniabdullah_bfaa.domain.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private  val list = ArrayList<User>()

    fun setList(users : ArrayList<User>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: UserItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(circleImageView)
                nameUser.text = user.login
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = UserItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return  list.size
    }

}