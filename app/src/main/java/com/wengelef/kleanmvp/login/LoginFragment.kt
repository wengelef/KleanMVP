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

package com.wengelef.kleanmvp.login

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.wengelef.kleanmvp.MainActivity
import com.wengelef.kleanmvp.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fr_login.*
import com.wengelef.kleanmvp.login.LoginView.LoginViewState as LoginViewState
import javax.inject.Inject

class LoginFragment : Fragment(), LoginView {

    @Inject lateinit var presenter: LoginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.start(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.destroy()
    }

    override fun getLoginClicks(): Observable<Any> = RxView.clicks(login_button)

    override fun getPassInput(): String = pass_input.text.toString()

    override fun getMailInput(): String = mail_input.text.toString()

    override fun getTextChanges(): Observable<String> = RxTextView.textChanges(pass_input).mergeWith { RxTextView.textChanges(mail_input) }
            .map { charSequence -> charSequence.toString() }

    override fun updateUI(viewState: LoginView.LoginViewState) {
        progress_bar.visibility = View.INVISIBLE
        when (viewState) {
            is LoginViewState.Success -> Snackbar.make(view!!, "${viewState.user.email} logged in", Snackbar.LENGTH_SHORT).show()
            is LoginViewState.Progress -> progress_bar.visibility = if (viewState.visible) View.VISIBLE else View.INVISIBLE
            is LoginViewState.Error -> Snackbar.make(view!!, viewState.message, Snackbar.LENGTH_SHORT).show()
        }
    }
}