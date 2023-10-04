package com.milansolanki.mvvmandroid.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.milansolanki.mvvmandroid.navigation.NavRoute
import com.milansolanki.mvvmandroid.ui.home.HomeScreen
import com.milansolanki.mvvmandroid.ui.home.HomeViewModel
import com.milansolanki.mvvmandroid.ui.login.LoginScreen
import com.milansolanki.mvvmandroid.ui.login.LoginViewModel
import com.milansolanki.mvvmandroid.ui.profile.ProfileScreen
import com.milansolanki.mvvmandroid.ui.profile.ProfileViewModel
import com.milansolanki.mvvmandroid.ui.register.RegisterScreen
import com.milansolanki.mvvmandroid.ui.register.RegisterViewModel
import com.milansolanki.mvvmandroid.ui.splash.Splash
import com.milansolanki.mvvmandroid.ui.ui.theme.MVVMAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.HomeScreen.route) {
        composable(NavRoute.SplashScreen.route) { Splash(navController) }
        composable(NavRoute.LoginScreen.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(viewModel, navController)
        }
        composable(NavRoute.RegisterScreen.route) {
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(viewModel, navController)
        }
        composable(NavRoute.HomeScreen.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(viewModel, navController)
        }
        composable(NavRoute.ProfileScreen.route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(viewModel, navController)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(
    showBackground = true,
    name = "mj",
    device = "spec:width=1080px,height=1920px",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)

@Composable
fun GreetingPreview() {
    MVVMAndroidTheme {
        Scaffold {

        }
    }
}






