package com.wengelef.kleanmvp.data

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val userDB: UserDb,
        private val userService: UserService) : UserRepository {
    override fun getUsers(): Observable<DataState<List<UserEntity>>> {
        return Observable.concat(
                userDB.getUsers().subscribeOn(Schedulers.computation()),
                userService.getUsers().subscribeOn(Schedulers.io())
                        .doOnNext { users -> userDB.saveUsers(users) })
                .filter { users -> users.isNotEmpty() }
                .firstElement().toObservable()
                .flatMap { Observable.just(DataState.Valid(it)) }
    }
}