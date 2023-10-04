package com.milansolanki.mvvmandroid.navigation

sealed class NavRoute(val route: String) {
    data object SplashScreen : NavRoute("splash")
    data object LoginScreen : NavRoute("login")
    data object RegisterScreen : NavRoute("register")
    data object HomeScreen : NavRoute("home")
    data object ProfileScreen : NavRoute("profile")
}
