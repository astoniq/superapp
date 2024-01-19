package com.astoniq.superapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.astoniq.superapp.core.common.navigation.Screens
import com.astoniq.superapp.feature.hub.HubScreen
import com.astoniq.superapp.feature.portal.Portal
import com.astoniq.superapp.feature.portal.PortalScreen
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

        composable(route = Screens.ExpenseInsight.route) {
            PortalScreen(Portal("test"))
        }

        composable(
            route = "portal/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            PortalScreen(Portal(arguments.getString("name", "")))
        }

        composable(route = Screens.Hub.route) {
            HubScreen(navHostController = navHostController)
        }

    }
}
