package com.milansolanki.mvvmandroid.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.milansolanki.mvvmandroid.navigation.ScreenEvents
import com.milansolanki.mvvmandroid.utils.printLog

@Composable
fun AppScreenEventsNotifier(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    event: ScreenEvents?,
    sendEvent: (ScreenEvents) -> Unit
) {

    val showLoading = remember {
        mutableStateOf(false)
    }

    var message = ""
    if (event is ScreenEvents.ShowSnackBar) {
        message = if (event.resourceId != 0)
            stringResource(id = event.resourceId!!)
        else
            event.message!!
    }

    LaunchedEffect(event) {

        when (event) {
            is ScreenEvents.Navigate -> {
                showLoading.value = false
                navController.navigate(event.route)
                sendEvent.invoke(ScreenEvents.Clean)
            }

            is ScreenEvents.ShowSnackBar -> {
                showLoading.value = false
                snackBarHostState.showSnackbar(message = message)
                message.printLog()
                sendEvent.invoke(ScreenEvents.Clean)
            }

            is ScreenEvents.ShowLoading -> {
                showLoading.value = event.isLoading
                sendEvent.invoke(ScreenEvents.Clean)
            }

            null, ScreenEvents.Clean -> {
                return@LaunchedEffect
            }
        }
    }

    when {
        showLoading.value -> {
            Dialog(
                onDismissRequest = { /*TODO*/ },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false,
                )
            ) {
                Box {
                    CircularProgressIndicator()
                }
            }
        }
    }
}