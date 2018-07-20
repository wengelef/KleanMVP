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

package com.wengelef.kleanmvp.signup

import com.wengelef.kleanmvp.data.FirebaseUser
import com.wengelef.kleanmvp.mvp.BaseView
import io.reactivex.Observable

interface SignupView : BaseView {

    sealed class SignupViewState {
        class Success(val user: FirebaseUser) : SignupViewState()
        class Progress(val visible: Boolean) : SignupViewState()
        class Error(val message: String) : SignupViewState()
    }

    fun getSignupClicks(): Observable<Any>

    fun getPassInput(): String
    fun getMailInput(): String
    fun getTextChanges(): Observable<String>

    fun updateUI(viewState: SignupViewState)
}