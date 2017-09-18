package com.wengelef.kleanmvp.data

import io.reactivex.Observable

class UserRoom(private val userDao: UserDao) : UserDb {

    override fun getUsers(): Observable<List<UserEntity>> {
        return userDao.getUsers().first(emptyList()).toObservable()
    }

    override fun saveUsers(users: List<UserEntity>) {
        userDao.saveUsers(users)
    }
}