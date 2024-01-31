package com.example.hw21.data.global.common

sealed class Resource<T: Any> {
    data class Loading<T: Any>(val loading: Boolean) : Resource<T>()
    data class Success<T: Any>(val response: T) : Resource<T>()
    data class Error<T: Any>(val message: String, val throwable: Throwable) : Resource<T>()
}