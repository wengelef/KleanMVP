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

package com.wengelef.kleanmvp.signup.domain

import com.wengelef.kleanmvp.data.UserRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignupInteractorImpl @Inject constructor(
        private val userRepository: UserRepository,
        private val inputValidator: SignupInputValidator
) : SignupInteractor {
    override fun signupUser(mail: String, pass: String): Observable<SignupInteractor.SignupState> {
        return inputValidator.validateInputs(mail, pass)
                .flatMap { inputValidation ->
                    when (inputValidation) {
                        is SignupInputValidator.InputValidation.Valid -> userRepository.registerUser(inputValidation.mail, inputValidation.pass)
                                .map { dataState ->
                                    when (dataState) {
                                        is UserRepository.SignupDataState.Succes -> SignupInteractor.SignupState.Success(dataState.user)
                                        is UserRepository.SignupDataState.Failure -> SignupInteractor.SignupState.Failure(dataState.throwable.toString())
                                        is UserRepository.SignupDataState.UserExists -> SignupInteractor.SignupState.Failure("User Exists")
                                    }
                                }
                        is SignupInputValidator.InputValidation.Invalid -> Observable.just(SignupInteractor.SignupState.InvalidInput())
                    }
                }
                .startWith(SignupInteractor.SignupState.Progress())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}