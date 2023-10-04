package com.milansolanki.mvvmandroid.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.milansolanki.mvvmandroid.R
import com.milansolanki.mvvmandroid.ui.composables.AppIcon
import com.milansolanki.mvvmandroid.ui.composables.AppScaffold
import com.milansolanki.mvvmandroid.ui.composables.AppTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel, navController: NavHostController) {
    val scrollableState =
        rememberScrollState()

    AppScaffold(viewModel = viewModel, navController = navController, topBar = {
        TopAppBar(
            title = { }, navigationIcon = {
                AppIcon(
                    icon = Icons.Default.ArrowBack,
                    contentDescription = R.string.lbl_close,
                    onClick = {
                        navController.popBackStack()
                    }
                )
            })
    }) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .verticalScroll(scrollableState),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                AppTextField(
                    label = stringResource(id = R.string.lbl_firstname),
                    fieldState = viewModel.firstName.collectAsState(),
                    onTextChanged = viewModel::updateFirstName,
                    keyboardType = KeyboardType.Text,
                    errorState = viewModel.firstNameError.collectAsState()
                )

                AppTextField(
                    label = stringResource(id = R.string.lbl_lastname),
                    fieldState = viewModel.lastName.collectAsState(),
                    onTextChanged = viewModel::updateLastName,
                    keyboardType = KeyboardType.Text,
                    topSpace = 8,
                    errorState = viewModel.lastNameError.collectAsState()
                )

                AppTextField(
                    label = stringResource(id = R.string.lbl_email),
                    fieldState = viewModel.email.collectAsState(),
                    onTextChanged = viewModel::updateEmail,
                    keyboardType = KeyboardType.Email,
                    topSpace = 8,
                    errorState = viewModel.emailError.collectAsState()
                )

                AppTextField(
                    label = stringResource(id = R.string.lbl_password),
                    fieldState = viewModel.password.collectAsState(),
                    onTextChanged = viewModel::updatePassword,
                    topSpace = 8,
                    errorState = viewModel.passwordError.collectAsState(),
                    keyboardType = KeyboardType.Password
                )

                AppTextField(
                    label = stringResource(id = R.string.lbl_confirm_password),
                    fieldState = viewModel.confirmPassword.collectAsState(),
                    onTextChanged = viewModel::updateConfirmPassword,
                    topSpace = 8,
                    errorState = viewModel.confirmPasswordError.collectAsState(),
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                )

                Button(
                    onClick = { viewModel.register() },
                    enabled = viewModel.isValidUserInput.collectAsState().value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                ) {
                    Text(stringResource(id = R.string.lbl_register))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text(
                        text = stringResource(id = R.string.msg_already_have_an_account),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    ClickableText(
                        text = AnnotatedString(stringResource(id = R.string.lbl_login)),
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        ),
                        onClick = {
                            navController.popBackStack()
                        })
                }
            }
        }
    }
}