package com.kc.marvelapp.util

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error(val message: String) : Resource<Nothing>()
    class Loading(val isLoading: Boolean = true): Resource<Nothing>()
}