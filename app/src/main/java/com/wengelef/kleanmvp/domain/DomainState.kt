package com.wengelef.kleanmvp.domain

import com.wengelef.kleanmvp.data.State

sealed class DomainState<DataType> : State {
    data class Valid<DataType>(val data: DataType) : DomainState<DataType>()
    data class Invalid<DataType>(val reason: String) : DomainState<DataType>()
}