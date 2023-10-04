package com.milansolanki.mvvmandroid.models

import androidx.annotation.StringRes

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String?="", @StringRes var messageResourceId: Int?=0) :
        Result<Nothing>()
}
