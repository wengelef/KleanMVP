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

package com.wengelef.kleanmvp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import io.reactivex.Observable

class FirebaseUserService : UserService {

    private val auth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String): Observable<UserService.UserResponse> {
        return Observable.create { emitter ->
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val firebaseUser = auth.currentUser?.let { user -> FirebaseUser(user.displayName, user.email!!, user.phoneNumber) }!!
                            emitter.onNext(UserService.UserResponse.Success(firebaseUser))
                        } else {
                            if (task.exception is FirebaseAuthInvalidUserException) {
                                emitter.onNext(UserService.UserResponse.UserNotFound())
                            } else {
                                emitter.onNext(UserService.UserResponse.Failure(task.exception!!))
                            }
                        }
                        emitter.onComplete()
                    }
        }
    }

    override fun register(email: String, password: String): Observable<UserService.SignupResponse> {
        return Observable.create { emitter ->
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val firebaseUser = auth.currentUser?.let { user -> FirebaseUser(user.displayName, user.email!!, user.phoneNumber) }!!
                            emitter.onNext(UserService.SignupResponse.Success(firebaseUser))
                        } else {
                            if (task.exception is FirebaseAuthUserCollisionException) {
                                emitter.onNext(UserService.SignupResponse.UserExists())
                            } else {
                                emitter.onNext(UserService.SignupResponse.Failure(task.exception!!))
                            }
                        }
                        emitter.onComplete()
                    }
        }
    }
}