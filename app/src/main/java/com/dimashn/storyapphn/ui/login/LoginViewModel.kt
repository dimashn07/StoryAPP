package com.dimashn.storyapphn.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimashn.storyapphn.data.model.User
import com.dimashn.storyapphn.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun userLogin(email: String, password: String) =
        userRepository.userLogin(email, password)

    fun saveUser(user: User) {
        viewModelScope.launch {
            userRepository.saveUserData(user)
        }
    }
}