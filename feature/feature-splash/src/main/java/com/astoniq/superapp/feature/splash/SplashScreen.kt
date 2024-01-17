package com.astoniq.superapp.feature.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.astoniq.superapp.core.common.theme.DarkGreen

@Composable
fun SplashScreen(navHostController: NavHostController) {

    val degrees = remember {
        Animatable(0f)
    }

    Splash(scale = degrees.value)
}

@Composable
fun Splash(scale: Float) {
    Box(
        modifier = Modifier
            .background(DarkGreen)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .rotate(scale)
                .size(250.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Splash Screen"
        )
    }
}