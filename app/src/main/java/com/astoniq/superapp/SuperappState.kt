package com.astoniq.superapp

import com.astoniq.superapp.core.model.ThemeType

sealed interface SuperappState {
    data object Loading : SuperappState
    data class Success(val theme: ThemeType, val destination: String) : SuperappState
}