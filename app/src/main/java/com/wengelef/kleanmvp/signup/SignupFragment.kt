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
import com.wengelef.kleanmvp.data.FirebaseUser
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fr_signup.*
import javax.inject.Inject

class SignupFragment : Fragment(), SignupView {

    @Inject
    lateinit var presenter: SignupPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_signup, container, false)
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

    override fun getSignupClicks(): Observable<Any> = RxView.clicks(sign_up_button)

    override fun getPassInput(): String = pass_input.text.toString()

    override fun getMailInput(): String = mail_input.text.toString()

    override fun getTextChanges(): Observable<String> = RxTextView.textChanges(pass_input)
            .mergeWith { RxTextView.text(mail_input) }
            .map { charSequence: CharSequence -> charSequence.toString() }

    override fun showSignupSuccess(user: FirebaseUser) {
        Snackbar.make(view!!, "${user.email} signed up", Snackbar.LENGTH_SHORT).show()
    }

    override fun showError(message: String) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun showProgress(visible: Boolean) {
        progress_bar.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }
}

