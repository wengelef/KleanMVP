package com.wengelef.kleanmvp.data

import io.reactivex.Observable

interface UserRepository {
    fun getUsers(): Observable<DataState<List<UserEntity>>>
}