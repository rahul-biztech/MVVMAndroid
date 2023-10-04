package com.milansolanki.mvvmandroid.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.milansolanki.mvvmandroid.models.User
import javax.inject.Inject


class UserPreferences @Inject constructor(private val preferences: SharedPreferences) {

    fun saveUserInfo(userModel: User?) {
        preferences.edit()
            .putString("user", if (userModel != null) Gson().toJson(userModel) else null).apply()
    }

    fun getUserInfo(): User? {
        val userString = preferences.getString("user", null)
        return Gson().fromJson(userString, User::class.java)
    }
}