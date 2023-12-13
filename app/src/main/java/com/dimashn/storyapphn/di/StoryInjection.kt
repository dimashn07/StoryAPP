package com.dimashn.storyapphn.di

import android.content.Context
import com.dimashn.storyapphn.data.network.api.ApiConfig
import com.dimashn.storyapphn.data.preference.UserPreferences
import com.dimashn.storyapphn.data.preference.dataStore
import com.dimashn.storyapphn.data.repository.StoryRepository
import com.dimashn.storyapphn.data.repository.UserRepository

object StoryInjection {
    fun provideStoryRepository(context: Context): StoryRepository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiClient()
        return StoryRepository.getInstance(preferences, apiService)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiClient()
        return UserRepository(preferences, apiService)
    }
}