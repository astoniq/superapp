package com.astoniq.superapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.astoniq.superapp.core.common.theme.DarkGreen
import com.astoniq.superapp.core.common.theme.LightBlack100

@Composable
fun SuperappScreen(
    uiState: SuperappState,
    authCode: String?
) {
    when (uiState) {
        is SuperappState.Loading -> {}
        is SuperappState.Success -> {}
    }
}

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)


@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = modifier.height(56.dp),
        backgroundColor = Color.White,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry
                .value?.destination?.route

            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = DarkGreen,
                unselectedContentColor = DarkGreen,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = modifier.size(25.dp),
                            tint = if (selected) DarkGreen else LightBlack100
                        )
                        Text(
                            text = item.title,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp,
                            color = LightBlack100
                        )
                    }
                })
        }
    }
}