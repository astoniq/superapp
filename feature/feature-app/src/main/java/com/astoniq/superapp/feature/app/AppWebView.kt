package com.astoniq.superapp.feature.app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.navigation.NavController
import com.astoniq.superapp.core.common.navigation.Screens
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

fun appWebView(
    context: Context,
    url: String,
    savedInstanceState: Bundle?,
    model: AppViewModel,
    navController: NavController
) = AppWebView(context).apply {

    val objectMapper = ObjectMapper()
        .registerModule(KotlinModule())

    val bridge = AppBridge(objectMapper, this)

    bridge.add("open") { ->
        runOnUiThread {
            navController.navigate(Screens.Splash.route)
        }
    }

    bridge.add("show") { promise: AppPromise, value: String ->
       promise.resolve(value)
    }

    // apply javascript interface from native
    expose("AndroidBridge", bridge)

    if (savedInstanceState !== null) {
        restoreState(savedInstanceState)
    } else {
        loadUrl(url)
    }
}

@SuppressLint("SetJavaScriptEnabled")
class AppWebView(context: Context) : WebView(context), AppExecutor {

    private val uiScope: CoroutineScope = CoroutineScope(
        Dispatchers.Main + SupervisorJob()
    )

    init {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    internal fun expose(name: String, bridge: AppBridge) {
        addJavascriptInterface(object {
            @JavascriptInterface
            @Suppress("unused")
            fun postMessage(json: String) {
                try {
                    val runnableCode = Runnable { bridge.receive(json) }
                    handler.post(runnableCode)
                } catch (e: Exception) {
                    Log.d("AppWebView", "Unable to handle incoming json. Ignoring.")
                }
            }
        }, name)
    }

    internal fun runOnUiThread(action: suspend () -> Unit) =
        uiScope.launch { action() }

    override fun execute(script: String) {
        runOnUiThread {
            evaluateJavascript(script) {}
        }
    }
}

