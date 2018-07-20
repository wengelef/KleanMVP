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
                .map { loginDomainState ->
                    when (loginDomainState) {
                        is LoginInteractor.LoginState.Success -> LoginView.LoginViewState.Success(loginDomainState.firebaseUser)
                        is LoginInteractor.LoginState.InvalidInput -> LoginView.LoginViewState.Error("Invalid Input")
                        is LoginInteractor.LoginState.Error -> LoginView.LoginViewState.Error(loginDomainState.message)
                        is LoginInteractor.LoginState.Progress -> LoginView.LoginViewState.Progress(true)
                    }
                }
                .subscribe { loginViewState -> view.updateUI(loginViewState) })
    }

    override fun destroy() {
        disposables.clear()
    }
}