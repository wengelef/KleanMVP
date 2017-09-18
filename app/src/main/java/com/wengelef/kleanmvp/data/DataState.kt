package com.wengelef.kleanmvp.data

sealed class DataState<DataType> : State {

    //override fun map(): (inState: State) -> State = { Invalid("default") }

    data class Valid<DataType>(val data: DataType) : DataState<DataType>()
    data class Invalid<DataType>(val reason: String) : DataState<DataType>()
}