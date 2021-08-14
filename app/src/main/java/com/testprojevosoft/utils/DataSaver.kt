package com.testprojevosoft.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first

class DataSaver {

    companion object {

        suspend fun saveAuthorizationState(dataStore: DataStore<Preferences>, key: String, isAuthorized: Boolean) {
            val dataStoreKey = booleanPreferencesKey(key)
            dataStore.edit { settings ->
                settings[dataStoreKey] = isAuthorized
            }
        }

        suspend fun readAuthorizationState(dataStore: DataStore<Preferences>, key: String): Boolean {
            val dataStoreKey = booleanPreferencesKey(key)
            val preferences = dataStore.data.first()
            return preferences[dataStoreKey] ?: false
        }
    }
}