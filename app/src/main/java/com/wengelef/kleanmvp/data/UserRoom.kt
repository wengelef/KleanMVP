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

import io.reactivex.Observable

class UserRoom(private val userDao: UserDao) : UserDb {
    override fun getUsers(): Observable<List<UserEntity>> {
        return userDao.getUsers().first(emptyList()).toObservable()
    }

    override fun getFollowedUsers(): Observable<List<UserEntity>> {
        return userDao.getFollowedUsers().first(emptyList()).toObservable()
    }

    override fun saveUsers(users: List<UserEntity>) {
        userDao.saveUsers(users)
    }

    override fun getUserForName(name: String): Observable<UserEntity> {
        return userDao.getUserForName(name).firstElement().toObservable()
    }

    override fun saveUser(user: UserEntity): Observable<UserEntity> {
        userDao.saveUser(user)
        return userDao.getUserForName(user.name).toObservable()
    }
}