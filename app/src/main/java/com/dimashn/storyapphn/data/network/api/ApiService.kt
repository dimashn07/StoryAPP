package com.dimashn.storyapphn.data.network.api

import com.dimashn.storyapphn.data.network.response.LoginResponse
import com.dimashn.storyapphn.data.network.response.PostStoryResponse
import com.dimashn.storyapphn.data.network.response.RegisterResponse
import com.dimashn.storyapphn.data.network.response.StoryResponse
import com.dimashn.storyapphn.data.request.LoginRequest
import com.dimashn.storyapphn.data.request.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse


    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("stories")
    suspend fun getStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): StoryResponse

    @GET("stories")
    suspend fun getStoryLocation(
        @Header("Authorization") token: String,
        @Query("location") location : Int = 1,
    ) : StoryResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): PostStoryResponse


}