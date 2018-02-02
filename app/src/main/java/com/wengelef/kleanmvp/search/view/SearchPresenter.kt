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

package com.wengelef.kleanmvp.search.view

import com.wengelef.kleanmvp.domain.DomainState
import com.wengelef.kleanmvp.domain.User
import com.wengelef.kleanmvp.domain.UserInteractor
import com.wengelef.kleanmvp.mvp.Presenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val userInteractor: UserInteractor) : Presenter<SearchView> {

    private val userModelMapper: (User) -> UserViewModel = { user -> UserViewModel(user.name, user.avatarUrl, user.htmlUrl, user.isFollowing) }
    private val userViewModelMapper: (UserViewModel) -> User = { userViewModel -> User(userViewModel.name, userViewModel.avatarUrl, userViewModel.htmlUrl, userViewModel.isFollowing) }

    private val disposables = CompositeDisposable()

    override fun start(view: SearchView) {
        disposables.add(view.searchInputChanges()
                .flatMap {
                    userInteractor.getUsers({ user -> user.name.startsWith(it, true) })
                        .firstOrError().toObservable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { state ->
                    when (state) {
                        is DomainState.Valid -> view.showUsers(state.data.map(userModelMapper))
                        is DomainState.Invalid -> view.showError(state.reason)
                    }
                })

        disposables.add(userInteractor.getUsers({ user: User -> user.name.isNotBlank() })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { usersState ->
                    when (usersState) {
                        is DomainState.Valid -> view.showUsers(usersState.data.map(userModelMapper))
                        is DomainState.Invalid -> view.showError(usersState.reason)
                    }
                })

        disposables.add(view.userClicks()
                .map(userViewModelMapper)
                .subscribe { user -> userInteractor.followUser(user) })
    }

    override fun destroy() {
        disposables.dispose()
    }
}