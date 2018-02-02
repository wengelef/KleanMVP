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

    private val userEntityMapper: (UserEntity) -> User = { userEntity -> User(userEntity.name, userEntity.avatarUrl, userEntity.avatarUrl) }

    override fun getUsers(predicate: (user: User) -> Boolean): Observable<DomainState<List<User>>> {
        return userRepository.getUsers()
                .map { dataState ->
                    when (dataState) {
                        is DataState.Success -> DomainState.Valid(dataState.data.map(userEntityMapper).filter(predicate))
                        is DataState.Failure -> DomainState.Invalid<List<User>>(dataState.reason)
                    }
                }
    }

    override fun followUser(user: User): Observable<DomainState<Boolean>> {
        return userRepository.getUserForName(user.name)
                .flatMap { state: DataState<UserEntity> ->
                    when (state) {
                        is DataState.Success -> userRepository.saveUser(state.data.copy(isFollowing = !state.data.isFollowing))
                        is DataState.Failure -> Observable.just(DataState.Failure(state.reason))
                    }
                }
                .map { state: DataState<UserEntity> ->
                    when (state) {
                        is DataState.Success -> DomainState.Valid(state.data.isFollowing)
                        is DataState.Failure -> DomainState.Invalid<Boolean>(state.reason)
                    }
                }
    }
}

