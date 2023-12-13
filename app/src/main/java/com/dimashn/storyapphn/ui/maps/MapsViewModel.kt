package com.dimashn.storyapphn.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dimashn.storyapphn.data.model.User
import com.dimashn.storyapphn.data.repository.StoryRepository
import com.dimashn.storyapphn.data.repository.UserRepository

class MapsViewModel(private val userRepository: UserRepository, private val storyRepository: StoryRepository): ViewModel() {

    fun getStoryLocation(token: String) =
        storyRepository.getStoryLocation(token)

    fun getUser(): LiveData<User> {
        return userRepository.getUserData()
    }
}