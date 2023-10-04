package com.milansolanki.mvvmandroid.ui.profile


import androidx.lifecycle.viewModelScope
import com.milansolanki.mvvmandroid.models.Result
import com.milansolanki.mvvmandroid.models.User
import com.milansolanki.mvvmandroid.navigation.ScreenEvents
import com.milansolanki.mvvmandroid.repository.AppRepository
import com.milansolanki.mvvmandroid.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val appRepository: AppRepository) :
    BaseViewModel() {


    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        when (val result = appRepository.loggedInUserInfo()) {
            is Result.Error -> ScreenEvents.ShowSnackBar(
                result.message,
                result.messageResourceId
            )

            is Result.Success -> _user.value = result.data
        }
    }

    fun logout() {

        viewModelScope.launch {
            screenEvent.update { ScreenEvents.ShowLoading(true) }
            delay(3000)
            when (val result = appRepository.logoutUser()) {
                is Result.Error -> ScreenEvents.ShowSnackBar(
                    result.message,
                    result.messageResourceId
                )

                is Result.Success -> {
                    screenEvent.update { ScreenEvents.ShowLoading(false) }
                    _user.value = null
                }
            }
        }
    }
}