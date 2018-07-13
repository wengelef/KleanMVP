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

package com.wengelef.kleanmvp.di

import com.wengelef.kleanmvp.data.UserRepository
import com.wengelef.kleanmvp.login.domain.FirebaseLoginInteractor
import com.wengelef.kleanmvp.login.domain.LoginInputValidator
import com.wengelef.kleanmvp.login.domain.LoginInputValidatorImpl
import com.wengelef.kleanmvp.login.domain.LoginInteractor
import com.wengelef.kleanmvp.signup.domain.SignupInputValidator
import com.wengelef.kleanmvp.signup.domain.SignupInputValidatorImpl
import com.wengelef.kleanmvp.signup.domain.SignupInteractor
import com.wengelef.kleanmvp.signup.domain.SignupInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton @Module
class DomainModule {

    @Provides @Singleton fun provideLoginInputValidator(): LoginInputValidator = LoginInputValidatorImpl()

    @Provides @Singleton fun provideSignupInputValidator(): SignupInputValidator = SignupInputValidatorImpl()

    @Provides
    @Singleton
    fun provideLoginInteractor(userRepository: UserRepository, loginInputValidator: LoginInputValidator): LoginInteractor {
        return FirebaseLoginInteractor(userRepository, loginInputValidator)
    }

    @Provides
    @Singleton
    fun provideSignupInteractor(userRepository: UserRepository, signupInputValidator: SignupInputValidator): SignupInteractor {
        return SignupInteractorImpl(userRepository, signupInputValidator)
    }
}