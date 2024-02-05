package com.astoniq.superapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astoniq.superapp.core.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SuperappViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository
) : ViewModel() {

    // read user data from data store (theme, user is logged in)
    val uiState: StateFlow<SuperappState> = dataStoreRepository.readUserDataState
        .map {
            if (it.isUserLoggedIn) {
                SuperappState.Success(it.themeType, "home")
            } else {
                SuperappState.Success(it.themeType, "login")
            }
        }.stateIn(
            scope = viewModelScope,
            initialValue = SuperappState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )
}

