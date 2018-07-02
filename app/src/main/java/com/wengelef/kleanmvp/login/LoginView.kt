package com.wengelef.kleanmvp.login

import com.wengelef.kleanmvp.mvp.BaseView
import io.reactivex.Observable

interface LoginView : BaseView {
    fun getLoginClicks(): Observable<Any>

    fun getPassInput(): String
    fun getMailInput(): String
    fun getTextChanges(): Observable<String>
}