package com.milansolanki.mvvmandroid.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milansolanki.mvvmandroid.navigation.ScreenEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    protected val screenEvent= MutableStateFlow<ScreenEvents?>(null)
    val screenEventState= screenEvent.asStateFlow()

    fun sendEvent(event: ScreenEvents) {
        viewModelScope.launch {
            screenEvent.emit(event)
        }
    }
}