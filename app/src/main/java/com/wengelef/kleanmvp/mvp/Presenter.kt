package com.wengelef.kleanmvp.mvp

interface Presenter<T : BaseView> {

    fun start(view: T)
    fun destroy()
}

