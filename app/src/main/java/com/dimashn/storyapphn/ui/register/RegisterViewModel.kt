package com.dimashn.storyapphn.ui.register

import androidx.lifecycle.ViewModel
import com.dimashn.storyapphn.data.repository.UserRepository

class RegisterViewModel (private val userRepository: UserRepository) : ViewModel() {
    fun userRegister(name: String, email: String, password: String) =
        userRepository.userRegister(name, email, password)
}