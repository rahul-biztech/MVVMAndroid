package com.milansolanki.mvvmandroid.navigation

import androidx.annotation.StringRes


sealed class ScreenEvents {
    data class ShowSnackBar(val message: String? = "", @StringRes val resourceId: Int? = 0) :
        ScreenEvents()

    data class Navigate(val route: String) : ScreenEvents()
    data object Clean : ScreenEvents()
    data class ShowLoading(val isLoading: Boolean) : ScreenEvents()
}
