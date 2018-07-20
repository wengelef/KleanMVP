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

import com.wengelef.kleanmvp.mvp.Presenter
import com.wengelef.kleanmvp.signup.domain.SignupInteractor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SignupPresenter @Inject constructor(private val signupInteractor: SignupInteractor) : Presenter<SignupView> {

    private val disposables = CompositeDisposable()

    override fun start(view: SignupView) {
        disposables.add(view.getSignupClicks()
                .map { view.getMailInput() to view.getPassInput() }
                .flatMap { (mail, pass) -> signupInteractor.signupUser(mail, pass) }
                .map { signupState ->
                    when (signupState) {
                        is SignupInteractor.SignupState.Success -> SignupView.SignupViewState.Success(signupState.user)
                        is SignupInteractor.SignupState.Failure -> SignupView.SignupViewState.Error("Signup failed : ${signupState.message}")
                        is SignupInteractor.SignupState.InvalidInput -> SignupView.SignupViewState.Error("Invalid Input")
                        is SignupInteractor.SignupState.Progress -> SignupView.SignupViewState.Progress(true)
                    }
                }
                .subscribe { signupState -> view.updateUI(signupState) }
        )
    }

    override fun destroy() {
        disposables.clear()
    }
}