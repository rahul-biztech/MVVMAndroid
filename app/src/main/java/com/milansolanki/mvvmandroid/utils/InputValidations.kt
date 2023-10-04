package com.milansolanki.mvvmandroid.utils

import android.util.Patterns
import com.milansolanki.mvvmandroid.R

fun String.isValidString(msgId: Int): Int? {
    return if (this.isValidString()) null else msgId
}

fun String.isValidString(): Boolean {
    return this.trim().isNotEmpty() && this != "null"
}

fun String.isValidEmail(): Int? {
    if (!this.isValidString()) {
        return R.string.msg_email_required
    } else if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()) {
        return R.string.msg_invalid_email
    }
    return null
}

fun String.isValidPassword(): Int? {
    if (!this.isValidString()) {
        return R.string.msg_password_required
    } else if (this.length < 6) {
        return R.string.msg_password_must_be_6_characters_long
    }
    return null
}

fun String.isValidConfirmPassword(password: String): Int? {
    if (!this.isValidString()) {
        return R.string.msg_confirm_password_required
    } else if (this.length < 6) {
        return R.string.msg_password_must_be
    } else if (this != password) {
        return R.string.msg_confirm_password_should_be_same_as_password
    }
    return null
}
