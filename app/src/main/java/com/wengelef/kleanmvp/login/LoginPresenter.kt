package com.wengelef.kleanmvp.login

import com.wengelef.kleanmvp.login.domain.LoginInteractor
import com.wengelef.kleanmvp.mvp.Presenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val loginInteractor: LoginInteractor) : Presenter<LoginView> {

    private val disposables = CompositeDisposable()

    override fun start(view: LoginView) {
        disposables.add(view.getLoginClicks()
                .map { view.getMailInput() to view.getPassInput() }
                .flatMap { (email, pass) -> loginInteractor.login(email, pass) }
                .subscribe { loginState ->
                    view.showProgress(loginState is LoginInteractor.LoginState.Progress)
                    when (loginState) {
                        is LoginInteractor.LoginState.Success -> view.showLoginSuccess(loginState.firebaseUser)
                        is LoginInteractor.LoginState.Error -> view.showError(loginState.message)
                        is LoginInteractor.LoginState.InvalidInput -> view.showError("Invalid Input")
                    }
                })
    }

    override fun destroy() {
        disposables.clear()
    }
}

