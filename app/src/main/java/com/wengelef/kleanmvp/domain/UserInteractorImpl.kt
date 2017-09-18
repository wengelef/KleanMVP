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

package com.wengelef.kleanmvp.domain

import com.wengelef.kleanmvp.data.DataState
import com.wengelef.kleanmvp.data.UserEntity
import com.wengelef.kleanmvp.data.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class UserInteractorImpl @Inject constructor(private val userRepository: UserRepository) : UserInteractor {

    override fun getUsers(predicate: (user: UserEntity) -> Boolean): Observable<DomainState<List<UserEntity>>> {
        return userRepository.getUsers()
                .map { dataState ->
                    when (dataState) {
                        is DataState.Valid -> DomainState.Valid(dataState.data.filter(predicate))
                        is DataState.Invalid -> DomainState.Invalid<List<UserEntity>>(dataState.reason)
                    }
                }
    }
}