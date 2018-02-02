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

package com.wengelef.kleanmvp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable

class UserPrefs(context: Context, private val gson: Gson) : UserDb {

    private val keyUsers = "key_users"

    private val db = context.getSharedPreferences("Users", Context.MODE_PRIVATE)

    override fun getUsers(): Observable<List<UserEntity>> {
        return gson.fromJson<List<UserEntity>>(db.getString(keyUsers, ""), object : TypeToken<List<UserEntity>>() {}.type)?.let {
            Observable.just(it)
        } ?: Observable.just(emptyList())
    }

    override fun saveUsers(users: List<UserEntity>) {
        db.commit { putString(keyUsers, gson.toJson(users)) }
    }

    override fun getFollowedUsers(): Observable<List<UserEntity>> {
        return getUsers()
                .flatMapIterable { it }
                .filter { it.isFollowing }
                .toList().toObservable()
    }

    override fun getUserForName(name: String): Observable<UserEntity> {
        return getUsers()
                .flatMapIterable { it }
                .filter { userEntity -> userEntity.name == name }
                .firstOrError().toObservable()
    }

    override fun saveUser(user: UserEntity): Observable<UserEntity> {
        return getUsers()
                .map { userEntities ->
                    val mutableUsers = userEntities.toMutableList()
                    val index = mutableUsers.indexOfFirst { userEntity -> userEntity.id == user.id }
                    if (index == -1) {
                        mutableUsers.add(user)
                    } else {
                        mutableUsers[index] = user
                    }
                    saveUsers(mutableUsers)
                    user
                }
    }
}