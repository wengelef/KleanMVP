/*
 * Copyright (c) wengelef 2017
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wengelef.kleanmvp.search.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.wengelef.kleanmvp.R
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    var users: List<UserViewModel> = emptyList()

    val userClicks = PublishSubject.create<UserViewModel>()

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.name.text = users[position].name
        holder.itemView.url.text = users[position].htmlUrl

        holder.itemView.setOnClickListener { userClicks.onNext(users[position]) }

        Glide.with(holder.itemView.imageView.context)
                .load(users[position].avatarUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .dontAnimate()
                .centerCrop()
                .into(holder.itemView.imageView)

        holder.itemView.star.visibility = when (users[position].isFollowing) {
            true -> View.VISIBLE
            false -> View.INVISIBLE
        }
    }

    override fun getItemCount(): Int = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view)
}