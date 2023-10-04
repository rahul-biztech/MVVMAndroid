package com.milansolanki.mvvmandroid.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.milansolanki.mvvmandroid.R
import com.milansolanki.mvvmandroid.navigation.NavRoute
import com.milansolanki.mvvmandroid.ui.composables.AppIcon
import com.milansolanki.mvvmandroid.ui.composables.AppScaffold
import com.milansolanki.mvvmandroid.utils.AppAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavHostController) {

    val userState = viewModel.user.collectAsState()
    val showLogoutAlertDialog = remember {
        mutableStateOf(false)
    }

    when {
        showLogoutAlertDialog.value -> {
            AppAlertDialog(
                onDismissRequest = { showLogoutAlertDialog.value = false },
                onConfirmation = {
                    showLogoutAlertDialog.value = false
                    viewModel.logout()
                }, confirmButtonTitle = stringResource(id = R.string.lbl_logout),
                dialogTitle = stringResource(id = R.string.lbl_logout),
                dialogText = stringResource(id = R.string.msg_logout),
                icon = null
            )
        }
    }

    AppScaffold(
        viewModel = viewModel,
        navController = navController, topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.lbl_profile)) },
                navigationIcon = {

                    AppIcon(
                        icon = Icons.Default.ArrowBack,
                        contentDescription = R.string.lbl_back,
                        onClick = {
                            navController.popBackStack()
                        })
                })
        },
        content = {
            if (userState.value == null) {
                Column {

                    Text(
                        text = stringResource(R.string.msg_login)
                    )

                    Button(
                        onClick = {
                            navController.navigate(NavRoute.LoginScreen.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                    ) {
                        Text(stringResource(id = R.string.lbl_login))
                    }
                }
            } else {
                val user = userState.value
                Column(
                    modifier = Modifier
                        .padding(12.dp), verticalArrangement = Arrangement.Center
                ) {


                    ProfileTextField(
                        label = stringResource(id = R.string.lbl_firstname),
                        value = user?.firstName!!,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileTextField(
                        label = stringResource(id = R.string.lbl_lastname),
                        value = user.lastName!!,
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    ProfileTextField(
                        label = stringResource(id = R.string.lbl_email),
                        value = user.email!!,
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            showLogoutAlertDialog.value = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                    ) {
                        Text(stringResource(id = R.string.lbl_logout))
                    }
                }
            }
        })
}

@Composable
fun ProfileTextField(label: String, value: String) {
    OutlinedTextField(value = value,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
        enabled = true, readOnly = true,
        label = { Text(label) })
}

