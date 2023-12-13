package com.dimashn.storyapphn.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.dimashn.storyapphn.data.network.api.ApiService
import com.dimashn.storyapphn.data.network.response.LoginResponse
import com.dimashn.storyapphn.data.preference.UserPreferences
import com.dimashn.storyapphn.data.request.LoginRequest
import com.dimashn.storyapphn.data.Result
import com.dimashn.storyapphn.data.model.User
import com.dimashn.storyapphn.data.network.response.RegisterResponse
import com.dimashn.storyapphn.data.request.RegisterRequest

class UserRepository(private val pref: UserPreferences, private val apiService: ApiService) {

    fun userLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(LoginRequest(email, password))
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Login", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun userRegister(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(
                RegisterRequest(name, email, password)
            )
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserData(): LiveData<User> {
        return pref.getUserData().asLiveData()
    }

    suspend fun saveUserData(user: User) {
        pref.saveUserData(user)
    }

    suspend fun logout() {
        pref.logout()
    }
}