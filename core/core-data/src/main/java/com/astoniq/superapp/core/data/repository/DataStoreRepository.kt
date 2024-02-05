package com.astoniq.superapp.core.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import com.astoniq.superapp.core.data.repository.DataStoreRepository.PreferenceKey.THEME
import com.astoniq.superapp.core.data.repository.DataStoreRepository.PreferenceKey.USER_LOGGED_IN
import com.astoniq.superapp.core.model.ThemeType
import com.astoniq.superapp.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class DataStoreRepository(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(
        name = PREFERENCES_NAME
    )

    val readUserDataState: Flow<UserData> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
           UserData(
               isUserLoggedIn =  it[USER_LOGGED_IN] ?: false,
               themeType = ThemeType.entries[it[THEME] ?: ThemeType.AUTO.ordinal]
           )
        }

    private object PreferenceKey {
        val CODE_VERIFIER = stringPreferencesKey("code_verifier")
        val STATE = stringPreferencesKey("state")
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val USER_LOGGED_IN = booleanPreferencesKey("user_logged_in")
        val THEME = intPreferencesKey("theme")
    }

    private companion object {
        const val PREFERENCES_NAME = "data_preferences"
    }

    val readIsUserLoggedInState: Flow<Boolean> =
        context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {
                it[USER_LOGGED_IN] ?: false
            }

    suspend fun saveIsUserLoggedIn(exist: Boolean) {
        context.dataStore.edit {
            it[USER_LOGGED_IN] = exist
        }
    }


}