package com.dimashn.storyapphn.ui.main

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dimashn.storyapphn.data.model.Story
import com.dimashn.storyapphn.data.model.User
import com.dimashn.storyapphn.data.repository.StoryRepository
import com.dimashn.storyapphn.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository, private val storyRepository: StoryRepository) : ViewModel(){

    fun getStory(): LiveData<PagingData<Story>> {
        return  storyRepository.getStory().cachedIn(viewModelScope)
    }

    fun getUser(): LiveData<User> {
        return userRepository.getUserData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

}