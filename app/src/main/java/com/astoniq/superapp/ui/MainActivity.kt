package com.astoniq.superapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.fragment.app.FragmentManager
import androidx.navigation.compose.rememberNavController
import com.astoniq.superapp.R
import com.astoniq.superapp.core.common.navigation.Screens
import com.astoniq.superapp.feature.portal.Portal
import com.astoniq.superapp.feature.portal.PortalFragment
import com.astoniq.superapp.navigation.BottomNavBar
import com.astoniq.superapp.navigation.BottomNavItem
import com.astoniq.superapp.navigation.NavigationGraph


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val remNavController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavBar(
                        items = listOf(
                            BottomNavItem(
                                title = "Home",
                                route = Screens.Splash.route,
                                icon = ImageVector.vectorResource(id = R.drawable.home)
                            ),
                            BottomNavItem(
                                title = "Expenses",
                                route = Screens.ExpenseInsight.route,
                                icon = ImageVector.vectorResource(id = R.drawable.coins)
                            ),
                            BottomNavItem(
                                title = "Hub",
                                route = Screens.Hub.route,
                                icon = ImageVector.vectorResource(id = R.drawable.finance)
                            ),
                            BottomNavItem(
                                title = "Settings",
                                route = Screens.Settings.route,
                                icon = ImageVector.vectorResource(id = R.drawable.setting)
                            )
                        ),
                        navController = remNavController,
                        onItemClick = {item -> remNavController.navigate(item.route)})
                }
            ) {paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    NavigationGraph(navHostController = remNavController)
                }
            }
        }
    }
}

