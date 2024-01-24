package com.astoniq.superapp.feature.app

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.lifecycle.Lifecycle.Event.*

@Composable
fun AppScreen(
    navController: NavController,
    url: String,
    backHandlerEnabled: MutableState<Boolean>,
    model: AppViewModel = hiltViewModel()
) {

    var webView: AppWebView? by remember {
        mutableStateOf(null)
    }

    var savedBundle: Bundle? by rememberSaveable {
        mutableStateOf(null)
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle) {
        val saveState: (() -> Unit) -> Unit = { then ->
            val bundle = Bundle()
            // If the WebView exists, save its state
            webView?.apply {
                // Write the WebView's state to the bundle.
                saveState(bundle)

                // Arbitrary operation to run after data is saved.
                then()
            }
            savedBundle = bundle
        }

        // Add a lifecycle observer to react accordingly to lifecycle events.
        val statePreservingObserver = LifecycleEventObserver { _, event ->
            when (event) {
                // Need to ensure the state is saved so it can be reloaded
                // ON_RESUME/ON_START
                ON_PAUSE, ON_STOP -> saveState {}

                // Want to save the state as we are not sure why the Activity
                // has been destroyed; it could just be in the background and
                // destroyed by Android to free up resources, but it may come
                // back.
                ON_DESTROY -> saveState {
                }
                // Nothing needed on these events
                ON_CREATE, ON_RESUME, ON_START, ON_ANY -> {}
            }
        }

        lifecycle.addObserver(statePreservingObserver)

        onDispose {
            backHandlerEnabled.value = false
            lifecycle.removeObserver(statePreservingObserver)
        }
    }

    BackHandler(backHandlerEnabled.value) {
        val wv = webView
        if (wv?.canGoBack() == true) {
            wv.goBack()
        } else {
            navController.popBackStack()
        }
    }

    AndroidView(
        factory = {
            appWebView(it, url, savedBundle, model, navController).apply {
                webView = this
            }
        }, update = {
            savedBundle?.let { bundle -> it.restoreState(bundle) } ?: it.loadUrl(url)
        })

}