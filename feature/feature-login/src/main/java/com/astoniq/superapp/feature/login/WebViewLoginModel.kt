package com.astoniq.superapp.feature.login

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astoniq.superapp.feature.login.LoginState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.Error

@HiltViewModel
class WebViewLoginModel @Inject constructor(): ViewModel() {

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(NotLoggedIn)
    val loginState: StateFlow<LoginState> = _loginState.onEach {
        when (it) {
            NotLoggedIn -> Unit
            ObservingUrl -> Unit
            is Error -> Unit
            LoggedIn -> delay(3_000)
            else -> {}
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, NotLoggedIn)


    val authUrl: String
        get() {
            val state = "12345678"
            return Uri.Builder()
                .scheme("https")
                .authority("example.com")
                .appendPath("oauth2")
                .appendPath("authorize")
                .appendQueryParameter("client_id", "86a0a278-bd29-41a2-84f3-f4c24a9fb6ef")
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("state", state)
                .appendQueryParameter("redirect_uri", "")
                .appendQueryParameter("scope", "email")
                .build()
                .toString()
        }

    fun parseUrl(uri: Uri?) {
        Log.d("uri", uri.toString())
        if (uri != null ) {
            if (uri.getQueryParameter("error") == null) {
                _loginState.value = ObservingUrl
                uri.getQueryParameter("code")?.let {
                   Log.d("s", it)
                }
            } else {
                _loginState.value = LoginState.Error("Access denied")
            }
        }
    }
}

sealed class LoginState {
    data object NotLoggedIn : LoginState()
    data object ObservingUrl : LoginState()
    data class Error(val errorMessage: String) : LoginState()
    data object LoggedIn : LoginState()
}

sealed class ResultState<out T> {
    data object Running : ResultState<Nothing>()
    data class Finished<T>(val value: T) : ResultState<T>()
}