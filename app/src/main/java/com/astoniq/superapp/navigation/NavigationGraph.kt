package com.astoniq.superapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.astoniq.superapp.core.common.navigation.Screens
import com.astoniq.superapp.feature.splash.SplashScreen

@Composable
fun NavigationGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.Splash.route,
    ) {

        composable(route = Screens.Splash.route) {
            SplashScreen(navHostController = navHostController)
        }

    }
}
