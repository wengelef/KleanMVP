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
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val userService: UserService) : UserRepository {

    sealed class UserCacheState {
        class Valid(val user: FirebaseUser) : UserCacheState()
        class Expired : UserCacheState()
    }

    override fun login(mail: String, pass: String): Observable<UserRepository.LoginDataState> {
        return userService.login(mail, pass)
                .map { userResponse ->
                    when (userResponse) {
                        is UserService.UserResponse.Success -> UserRepository.LoginDataState.Succes(userResponse.user)
                        is UserService.UserResponse.Failure -> UserRepository.LoginDataState.Failure(userResponse.throwable)
                        is UserService.UserResponse.UserNotFound -> UserRepository.LoginDataState.UserNotFound()
                    }
                }
    }

    override fun registerUser(mail: String, pass: String): Observable<UserRepository.SignupDataState> {
        return userService.register(mail, pass)
                .map { userResponse ->
                    when (userResponse) {
                        is UserService.SignupResponse.Success -> UserRepository.SignupDataState.Succes(userResponse.user)
                        is UserService.SignupResponse.Failure -> UserRepository.SignupDataState.Failure(userResponse.throwable)
                        is UserService.SignupResponse.UserExists -> UserRepository.SignupDataState.UserExists()
                    }
                }
    }
}