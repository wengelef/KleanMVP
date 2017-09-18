package com.wengelef.kleanmvp.data

import io.reactivex.Observable
import retrofit2.http.GET

interface UserService {

    @GET("users")
    fun getUsers(): Observable<List<UserEntity>>
}