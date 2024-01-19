package com.astoniq.superapp.feature.portal

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PortalScreen(portal: Portal) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { PortalView(it, portal) })
}
