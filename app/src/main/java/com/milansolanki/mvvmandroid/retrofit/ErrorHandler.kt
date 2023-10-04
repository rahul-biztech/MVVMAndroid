package com.milansolanki.mvvmandroid.retrofit

import retrofit2.Response
import javax.inject.Inject


class ErrorHandler @Inject constructor() {
    fun <T> handleNetworkError(response: Response<T>): String {
        return when (response.code()) {
            401 -> "UnAuthorised request."
            else -> "Unknown error with status code: ${response.code()}"
        }
    }
}