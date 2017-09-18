package com.wengelef.kleanmvp.domain

import com.wengelef.kleanmvp.data.DataState
import com.wengelef.kleanmvp.data.UserEntity
import com.wengelef.kleanmvp.data.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class UserInteractorImpl @Inject constructor(private val userRepository: UserRepository) : UserInteractor {

    override fun getUsers(predicate: (user: UserEntity) -> Boolean): Observable<DomainState<List<UserEntity>>> {
        return userRepository.getUsers()
                .map { dataState ->
                    when (dataState) {
                        is DataState.Valid -> DomainState.Valid(dataState.data.filter(predicate))
                        is DataState.Invalid -> DomainState.Invalid<List<UserEntity>>(dataState.reason)
                    }
                }
    }
}