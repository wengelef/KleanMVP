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
}