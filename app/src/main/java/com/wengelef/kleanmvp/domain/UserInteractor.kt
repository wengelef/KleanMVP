package com.wengelef.kleanmvp.domain

import com.wengelef.kleanmvp.data.UserEntity
import io.reactivex.Observable

interface UserInteractor {

    fun getUsers(predicate: (user: UserEntity) -> Boolean = { true }): Observable<DomainState<List<UserEntity>>>
}

