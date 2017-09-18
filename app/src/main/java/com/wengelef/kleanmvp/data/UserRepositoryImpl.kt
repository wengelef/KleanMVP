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
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val userDB: UserDb,
        private val userService: UserService) : UserRepository {
    override fun getUsers(): Observable<DataState<List<UserEntity>>> {
        return Observable.concat(
                userDB.getUsers().subscribeOn(Schedulers.computation()),
                userService.getUsers().subscribeOn(Schedulers.io())
                        .doOnNext { users -> userDB.saveUsers(users) })
                .filter { users -> users.isNotEmpty() }
                .firstElement().toObservable()
                .flatMap { Observable.just(DataState.Success(it)) }
    }
}