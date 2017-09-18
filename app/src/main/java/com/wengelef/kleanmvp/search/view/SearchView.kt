package com.wengelef.kleanmvp.search.view

import com.wengelef.kleanmvp.data.UserEntity
import com.wengelef.kleanmvp.mvp.BaseView
import io.reactivex.Observable

interface SearchView : BaseView {

    fun searchInputChanges(): Observable<String>

    fun showUsers(users: List<UserEntity>)
    fun showError(error: String)
}