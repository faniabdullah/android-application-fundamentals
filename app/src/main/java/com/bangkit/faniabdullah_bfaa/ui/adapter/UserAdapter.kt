package com.bangkit.faniabdullah_bfaa.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.faniabdullah_bfaa.R
import com.bangkit.faniabdullah_bfaa.databinding.UserItemsBinding
import com.bangkit.faniabdullah_bfaa.domain.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onItemFavoriteClickCallback: OnItemFavoriteClickCallback

    fun setOnItemFavoriteClickCallback(onItemFavoriteClickCallback: OnItemFavoriteClickCallback) {
        this.onItemFavoriteClickCallback = onItemFavoriteClickCallback
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: ArrayList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemsBinding.bind(itemView)
        fun bind(user: User) {
            binding.includeUserItems.viewUser.setOnClickListener {
                onItemClickCallback.onItemClicked(user)
            }
            binding.includeUserButton.toogleFavorite.isChecked = user.isfavorite
            val toggleButton = binding.includeUserButton.toogleFavorite
            binding.includeUserButton.toogleFavorite.setOnClickListener {
                onItemFavoriteClickCallback.onItemFavoriteClicked(user,
                    toggleButton)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_user)
                    .into(includeUserItems.circleImageView)
                includeUserItems.nameUser.text = user.login
                includeUserItems.tvUsername.text = user.type
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.user_items, parent, false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    interface OnItemFavoriteClickCallback {
        fun onItemFavoriteClicked(data: User, stateToogle: ToggleButton)
    }
}