package com.wengelef.kleanmvp.search.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.wengelef.kleanmvp.R
import com.wengelef.kleanmvp.data.UserEntity
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    var users: List<UserEntity> = emptyList()

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.name.text = users[position].name
        holder.itemView.url.text = users[position].htmlUrl

        Glide.with(holder.itemView.imageView.context)
                .load(users[position].avatarUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .dontAnimate()
                .centerCrop()
                .into(holder.itemView.imageView)
    }

    override fun getItemCount(): Int = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view)
}