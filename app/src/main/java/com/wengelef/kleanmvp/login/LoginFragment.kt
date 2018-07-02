package com.wengelef.kleanmvp.login

import android.os.Bundle
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
}