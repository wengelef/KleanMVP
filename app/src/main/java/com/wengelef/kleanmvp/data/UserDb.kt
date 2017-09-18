package com.wengelef.kleanmvp.data

import io.reactivex.Observable

interface UserDb {
    fun getUsers(): Observable<List<UserEntity>>
    fun saveUsers(users: List<UserEntity>)
}