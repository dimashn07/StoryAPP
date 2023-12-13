package com.dimashn.storyapphn.data.network.response

import com.dimashn.storyapphn.data.model.Story
import com.google.gson.annotations.SerializedName

data class StoryResponse(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("listStory")
    val listStory: List<Story>
)