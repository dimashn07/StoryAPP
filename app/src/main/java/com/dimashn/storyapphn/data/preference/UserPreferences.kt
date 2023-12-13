package com.dimashn.storyapphn.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dimashn.storyapphn.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "storyapphn")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUserData(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[NAME] ?: "",
                preferences[TOKEN] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun saveUserData(user: User) {
        dataStore.edit { preferences ->
            preferences[NAME] = user.name
            preferences[TOKEN] = user.token
            preferences[IS_LOGIN_KEY] = user.isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val lock = Object()

        @Volatile
        private var INSTANCE: UserPreferences? = null

        private val NAME = stringPreferencesKey("name")
        private val TOKEN = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLoggedIn")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            synchronized(lock) {
                return INSTANCE ?: UserPreferences(dataStore).also {
                    INSTANCE = it
                }
            }
        }
    }
}