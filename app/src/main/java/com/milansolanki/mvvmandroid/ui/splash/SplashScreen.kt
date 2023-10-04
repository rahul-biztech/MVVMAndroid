package com.milansolanki.mvvmandroid.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.milansolanki.mvvmandroid.R
import com.milansolanki.mvvmandroid.navigation.NavRoute
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        val navOptions = NavOptions.Builder().setPopUpTo(NavRoute.SplashScreen.route, true).build()
        navController.navigate(NavRoute.LoginScreen.route, navOptions)
    }
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(id = R.string.app_name), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
        )
    }
}