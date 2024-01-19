package com.astoniq.superapp.feature.hub

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HubScreenViewModel @Inject constructor(): ViewModel() {

    private val _apps = MutableStateFlow(arrayListOf("test", "settings"))

    val apps: StateFlow<ArrayList<String>> = _apps
}