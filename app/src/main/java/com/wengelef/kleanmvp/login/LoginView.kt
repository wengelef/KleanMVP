package com.wengelef.kleanmvp.login

import com.wengelef.kleanmvp.data.FirebaseUser
import com.wengelef.kleanmvp.mvp.BaseView
import io.reactivex.Observable

interface LoginView : BaseView {

    sealed class LoginViewState {
        class Success(val user: FirebaseUser) : LoginViewState()
        class Progress(val visible: Boolean) : LoginViewState()
        class Error(val message: String) : LoginViewState()
    }

    fun getLoginClicks(): Observable<Any>

    fun updateUI(viewState: LoginViewState)

    fun getPassInput(): String
    fun getMailInput(): String
    fun getTextChanges(): Observable<String>
}