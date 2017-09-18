package com.wengelef.kleanmvp.search.view

import com.wengelef.kleanmvp.data.UserEntity
import com.wengelef.kleanmvp.domain.DomainState
import com.wengelef.kleanmvp.domain.UserInteractor
import com.wengelef.kleanmvp.mvp.Presenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val userInteractor: UserInteractor) : Presenter<SearchView> {

    private var startQuery = 0L

    override fun start(view: SearchView) {
        Observable.merge(
                userInteractor.getUsers({ user: UserEntity -> user.name.isNotBlank() })
                        .doOnSubscribe { startQuery = System.currentTimeMillis() }
                        .doOnNext { Timber.e("DB Took ${System.currentTimeMillis() - startQuery} millis") },
                view.searchInputChanges()
                        .flatMap { userInteractor.getUsers({ user -> user.name.startsWith(it, true) }) })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { state ->
                    when (state) {
                        is DomainState.Valid -> view.showUsers(state.data)
                        is DomainState.Invalid -> view.showError(state.reason)
                    }
                }
    }

    override fun destroy() {
    }
}