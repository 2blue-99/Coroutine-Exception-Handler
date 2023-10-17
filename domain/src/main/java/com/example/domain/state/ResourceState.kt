package com.example.domain.state

import com.example.domain.model.MyData



sealed class ResourceState<T>{
    data class Success<T>(val data: T): ResourceState<T>()
    data class Error<T>(val data: T? = null, val failure: Failure = Failure.UnHandleError()): ResourceState<T>()
    class Loading<T>: ResourceState<T>()
}