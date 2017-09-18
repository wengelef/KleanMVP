package com.wengelef.kleanmvp.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Query("SELECT * FROM user") fun getUsers(): Flowable<List<UserEntity>>

    @Insert
    fun saveUsers(users: List<UserEntity>)
}