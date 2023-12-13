package com.dimashn.storyapphn.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dimashn.storyapphn.data.model.User
import com.dimashn.storyapphn.data.repository.StoryRepository
import com.dimashn.storyapphn.data.repository.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val userRepository: UserRepository, private val storyRepository: StoryRepository) : ViewModel() {

    fun addStory(token: String, file: MultipartBody.Part, description: RequestBody) = storyRepository.addStory(token, file, description)

    fun getUser(): LiveData<User> {
        return userRepository.getUserData()
    }
}