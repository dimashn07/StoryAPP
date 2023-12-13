package com.dimashn.storyapphn.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dimashn.storyapphn.data.repository.StoryRepository
import com.dimashn.storyapphn.data.repository.UserRepository
import com.dimashn.storyapphn.di.StoryInjection
import com.dimashn.storyapphn.ui.login.LoginViewModel
import com.dimashn.storyapphn.ui.maps.MapsViewModel
import com.dimashn.storyapphn.ui.story.AddStoryViewModel
import com.dimashn.storyapphn.ui.main.MainViewModel
import com.dimashn.storyapphn.ui.register.RegisterViewModel

class ViewModelFactory(private val userRepository: UserRepository, private val storyRepository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepository) as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository, storyRepository) as T
        }
        if (modelClass.isAssignableFrom(AddStoryViewModel::class.java)) {
            return AddStoryViewModel(userRepository, storyRepository) as T
        }
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(userRepository, storyRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.simpleName)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(StoryInjection.provideUserRepository(context), StoryInjection.provideStoryRepository(context))
            }.also { instance = it }
        }
    }
}