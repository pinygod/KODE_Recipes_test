package com.piny.kode_recipes_test.utils

sealed class Result<T>(val data: T? = null, val throwable: Throwable? = null) {
    class Success<T>(data: T) : Result<T>(data)
    class Loading<T>(data: T? = null) : Result<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : Result<T>(data, throwable)
    class Empty<T> : Result<T>()

    fun isLoading() = this is Loading
}