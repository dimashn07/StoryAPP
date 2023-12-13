package com.dimashn.storyapphn.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dimashn.storyapphn.data.Result
import com.dimashn.storyapphn.data.StoryPagingSource
import com.dimashn.storyapphn.data.model.Story
import com.dimashn.storyapphn.data.network.api.ApiService
import com.dimashn.storyapphn.data.network.response.PostStoryResponse
import com.dimashn.storyapphn.data.network.response.StoryResponse
import com.dimashn.storyapphn.data.preference.UserPreferences
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class StoryRepository(private val pref: UserPreferences, private val apiService: ApiService) {

    fun getStory(): LiveData<PagingData<Story>>{
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                StoryPagingSource(apiService, pref)
            }
        ).liveData
    }

    fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): LiveData<Result<PostStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.addStory(token, file, description)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStoryLocation(token: String): LiveData<Result<StoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStoryLocation(token, 1)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            preferences: UserPreferences,
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(preferences, apiService)
            }.also { instance = it }
    }
}