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

package com.wengelef.kleanmvp.login.domain

import io.reactivex.Observable
import javax.inject.Inject

class LoginInputValidatorImpl @Inject constructor() : LoginInputValidator {
    override fun validateInputs(email: String, pass: String): Observable<LoginInputValidator.InputValidation> {
        return when {
            email.isNotBlank() && pass.isNotBlank() -> Observable.just(LoginInputValidator.InputValidation.Valid(email, pass))
            else -> Observable.just(LoginInputValidator.InputValidation.Invalid())
        }
    }
}