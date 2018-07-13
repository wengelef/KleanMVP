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

package com.wengelef.kleanmvp.login.domain

import com.wengelef.kleanmvp.data.UserRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FirebaseLoginInteractor @Inject constructor(
        private val userRepository: UserRepository,
        private val inputValidator: LoginInputValidator
) : LoginInteractor {

    override fun login(email: String, password: String): Observable<LoginInteractor.LoginState> {
        return inputValidator.validateInputs(email, password)
                .flatMap { inputValidation ->
                    when (inputValidation) {
                        is LoginInputValidator.InputValidation.Valid -> userRepository.login(email, password)
                                .map { userState ->
                                    when (userState) {
                                        is UserRepository.LoginDataState.Succes -> LoginInteractor.LoginState.Success(userState.user)
                                        is UserRepository.LoginDataState.UserNotFound -> LoginInteractor.LoginState.Error("User Not Found")
                                        is UserRepository.LoginDataState.Failure -> LoginInteractor.LoginState.Error(userState.throwable.toString())
                                    }
                                }
                        is LoginInputValidator.InputValidation.Invalid -> Observable.just(LoginInteractor.LoginState.InvalidInput())
                    }
                }
                .startWith(LoginInteractor.LoginState.Progress())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}