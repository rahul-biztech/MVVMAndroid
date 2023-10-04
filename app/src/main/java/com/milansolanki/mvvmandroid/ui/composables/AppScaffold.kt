package com.milansolanki.mvvmandroid.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.milansolanki.mvvmandroid.ui.base.BaseViewModel

@Composable
fun AppScaffold(
    viewModel: BaseViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    horizontalPadding: Int? = 12,
    topBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val event = viewModel.screenEventState.collectAsState().value

    AppScreenEventsNotifier(navController, snackBarHostState, event, viewModel::sendEvent)

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = topBar
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    PaddingValues(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding(),
                        start = horizontalPadding!!.dp,
                        end = horizontalPadding.dp
                    )
                ),
        ) { content.invoke() }
    }
}