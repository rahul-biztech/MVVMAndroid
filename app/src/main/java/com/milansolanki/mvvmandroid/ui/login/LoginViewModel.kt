package com.milansolanki.mvvmandroid.ui.login

import androidx.lifecycle.viewModelScope
import com.milansolanki.mvvmandroid.R
import com.milansolanki.mvvmandroid.models.Result
import com.milansolanki.mvvmandroid.models.User
import com.milansolanki.mvvmandroid.navigation.NavRoute
import com.milansolanki.mvvmandroid.navigation.ScreenEvents
import com.milansolanki.mvvmandroid.repository.AppRepository
import com.milansolanki.mvvmandroid.ui.base.BaseViewModel
import com.milansolanki.mvvmandroid.utils.isValidEmail
import com.milansolanki.mvvmandroid.utils.isValidPassword
import com.milansolanki.mvvmandroid.utils.isValidString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val appRepository: AppRepository) :
    BaseViewModel() {
    private val _email = MutableStateFlow("")
    private val _passwordError = MutableStateFlow<Int?>(null)
    private val _emailError = MutableStateFlow<Int?>(null)
    private val _password = MutableStateFlow("")
    private val _isValidUserInput = MutableStateFlow(false)

    val email = _email.asStateFlow()
    val password = _password.asStateFlow()
    val isValidUserInput = _isValidUserInput.asStateFlow()
    val emailError = _emailError.asStateFlow()
    val passwordError = _passwordError.asStateFlow()

    fun updateEmail(email: String) {
        _email.value = email
        _emailError.value = email.isValidEmail()
        isValidUserInput()
    }

    fun updatePassword(password: String) {
        _password.value = password
        _passwordError.value = password.isValidPassword()
        isValidUserInput()
    }

    fun login() {
        viewModelScope.launch {

            val user = User(
                email = email.value,
                password = password.value,
                firstName = null,
                lastName = null,
                uId = null
            )

            screenEvent.update { ScreenEvents.ShowLoading(true) }

            delay(3000)
            when (val result = appRepository.loginUser(user)) {
                is Result.Success -> {
                    screenEvent.update { ScreenEvents.ShowSnackBar(resourceId = R.string.msg_login_success) }
                    screenEvent.update {
                        ScreenEvents.Navigate(NavRoute.HomeScreen.route)
                    }
                }

                is Result.Error -> screenEvent.update {
                    ScreenEvents.ShowSnackBar(
                        message = result.message,
                        resourceId = result.messageResourceId
                    )
                }
            }
        }
    }

    private fun isValidUserInput() {
        _isValidUserInput.value =
            (email.value.isValidString() && _emailError.value == null) && (password.value.isValidString() && _passwordError.value == null)
    }
}
