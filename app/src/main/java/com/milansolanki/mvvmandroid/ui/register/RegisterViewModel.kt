package com.milansolanki.mvvmandroid.ui.register


import androidx.lifecycle.viewModelScope
import com.milansolanki.mvvmandroid.R
import com.milansolanki.mvvmandroid.models.Result
import com.milansolanki.mvvmandroid.models.User
import com.milansolanki.mvvmandroid.navigation.NavRoute
import com.milansolanki.mvvmandroid.navigation.ScreenEvents
import com.milansolanki.mvvmandroid.repository.AppRepository
import com.milansolanki.mvvmandroid.ui.base.BaseViewModel
import com.milansolanki.mvvmandroid.utils.isValidConfirmPassword
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
class RegisterViewModel @Inject constructor(private val appRepository: AppRepository) :
    BaseViewModel() {

    private val _firstName = MutableStateFlow("")
    private val _lastName = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _confirmPassword = MutableStateFlow("")
    private val _firstNameError = MutableStateFlow<Int?>(null)
    private val _lastNameError = MutableStateFlow<Int?>(null)
    private val _emailError = MutableStateFlow<Int?>(null)
    private val _passwordError = MutableStateFlow<Int?>(null)
    private val _confirmPasswordError = MutableStateFlow<Int?>(null)
    private val _isValidUserInput = MutableStateFlow(false)

    val firstName = _firstName.asStateFlow()
    val lastName = _lastName.asStateFlow()
    val email = _email.asStateFlow()
    val password = _password.asStateFlow()
    val confirmPassword = _confirmPassword.asStateFlow()
    val firstNameError = _firstNameError.asStateFlow()
    val lastNameError = _lastNameError.asStateFlow()
    val emailError = _emailError.asStateFlow()
    val passwordError = _passwordError.asStateFlow()
    val confirmPasswordError = _confirmPasswordError.asStateFlow()

    val isValidUserInput = _isValidUserInput.asStateFlow()

    fun updateFirstName(firstName: String) {
        _firstName.value = firstName
        _firstNameError.value = firstName.isValidString(R.string.msg_first_name_required)
        isValidUserInput()
    }

    fun updateLastName(lastName: String) {
        _lastName.value = lastName
        _lastNameError.value = lastName.isValidString(R.string.msg_last_name_required)
        isValidUserInput()
    }

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

    fun updateConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
        _confirmPasswordError.value = confirmPassword.isValidConfirmPassword(_password.value)
        isValidUserInput()
    }

    fun register() {
        viewModelScope.launch {

            val user = User(
                email = email.value,
                password = password.value,
                firstName = firstName.value,
                lastName = lastName.value,
                uId = null
            )

            screenEvent.update { ScreenEvents.ShowLoading(true) }
            delay(3000)
            when (val result = appRepository.registerUser(user)) {
                is Result.Success -> {
                    screenEvent.update {
                        ScreenEvents.ShowSnackBar(
                            resourceId = R.string.msg_registration_success,
                        )
                    }
                    screenEvent.update {
                        ScreenEvents.Navigate(NavRoute.HomeScreen.route)
                    }
                }

                is Result.Error ->
                    screenEvent.update {
                        ScreenEvents.ShowSnackBar(
                            message = result.message,
                            resourceId = result.messageResourceId
                        )
                    }
            }
        }
    }

    private fun isValidUserInput() {

        _isValidUserInput.value = (firstName.value.isValidString() && firstNameError.value == null)
                && (lastName.value.isValidString() && lastNameError.value == null)
                && (email.value.isValidString() && emailError.value == null)
                && (password.value.isValidString() && passwordError.value == null)
                && (confirmPassword.value.isValidString() && confirmPasswordError.value == null)
    }
}