package com.astoniq.superapp.feature.login

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WebViewLoginActivity: ComponentActivity() {

    private lateinit var webView: WebView

    private val loginViewModel by viewModels<WebViewLoginModel>()

    @SuppressLint("SetJavaScriptEnabled")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this).apply {
            CookieManager.getInstance().removeAllCookies { }
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.url?.let { view?.loadUrl(it.toString()) }
                    return true;
                }
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    loginViewModel.parseUrl(Uri.parse(url))
                }
            }
            loadUrl(loginViewModel.authUrl)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState.collect {
                    when (it) {
                        is LoginState.NotLoggedIn,
                        is LoginState.ObservingUrl -> {
                            Log.d("s", "NotLoggedIn")
                        }
                        is LoginState.Error -> {
                            Log.d("s", "Error")
                            finish()
                        }
                        LoginState.LoggedIn -> finish()
                    }
                }
            }
        }

        setContentView(webView)
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}