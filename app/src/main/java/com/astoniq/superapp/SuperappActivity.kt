package com.astoniq.superapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.astoniq.superapp.core.common.theme.SuperappTheme
import com.astoniq.superapp.core.model.ThemeType
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SuperappActivity : AppCompatActivity() {

    private var authCode: String? = null;

    private val viewModel: SuperappViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        var uiState: SuperappState by mutableStateOf(SuperappState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        }

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                when (uiState) {
                    is SuperappState.Loading -> true
                    is SuperappState.Success -> false
                }
            }
        }

        setContent {
            SuperappTheme(darkTheme = shouldUseDarkTheme(uiState)) {
                SuperappScreen(uiState = uiState, authCode = authCode)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val uri = intent?.data
        if (uri != null && uri.toString().startsWith("Constants.redirectUri")) {
            val code = uri.getQueryParameter("code")
            if (code != null) {
                authCode = code
            }
        }
    }
}

@Composable
private fun shouldUseDarkTheme(state: SuperappState): Boolean = when (state) {
    is SuperappState.Loading -> isSystemInDarkTheme()
    is SuperappState.Success -> when (state.theme) {
        ThemeType.AUTO -> isSystemInDarkTheme()
        ThemeType.DARK -> true
        ThemeType.LIGHT -> false
    }
}
