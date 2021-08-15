package com.testprojevosoft

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class SettingsManager(val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

    suspend fun saveAuthorizationState(isAuthorized: Boolean) {
        try {
            context.dataStore.edit { settings ->
                settings[KEY_AUTHORIZATION_STATE] = isAuthorized
            }
        } catch (e: Exception) {
            Log.e("Manager", "Error", e)
        }

    }

    suspend fun readAuthorizationState(): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[KEY_AUTHORIZATION_STATE] ?: false
    }

    companion object {
        val KEY_AUTHORIZATION_STATE = booleanPreferencesKey("authorizationState")
    }
}