package com.astoniq.superapp.core.common.navigation

sealed class Screens(val route: String) {
    data object Home: Screens("home_screen")
    data object Settings : Screens("settings_screen")
    data object ExpenseInsight : Screens("expense_insight_screen")
    data object Reports : Screens("reports_screen")
    data object Splash : Screens("splash_screen")
}