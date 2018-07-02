package com.wengelef.kleanmvp.signup

import com.wengelef.kleanmvp.mvp.BaseView
import io.reactivex.Observable

interface SignupView : BaseView {
    fun getSignupClicks(): Observable<Any>

    fun getPassInput(): String
    fun getMailInput(): String
    fun getTextChanges(): Observable<String>
}