package com.dimashn.storyapphn.util

import com.dimashn.storyapphn.data.model.Login
import com.dimashn.storyapphn.data.model.Story
import com.dimashn.storyapphn.data.network.response.LoginResponse
import com.dimashn.storyapphn.data.network.response.PostStoryResponse
import com.dimashn.storyapphn.data.network.response.RegisterResponse

object DataDummy {

    fun generateDummyStory(): List<Story>{
        val item = arrayListOf<Story>()

        for (i in 0..100){
            val story = Story(
                "story-MnI5j7Bp4S5TUdHg",
                "Dimashn",
                "Hallo!",
                "https://4.bp.blogspot.com/-JSteHDHGpV4/UUCsnJmlnWI/AAAAAAAAEps/8A7zoM3T5jU/s1600/Wallpaper+Gambar+Burung+Betet.jpeg",
                "2023-11-07T06:35:20.810Z",
                -12.781,
                -17.187
            )
            item.add(story)
        }
        return item
    }

    fun generateDummyLogin(): LoginResponse {
        return LoginResponse(
            false,
            "token",
            Login(
                "id",
                "name",
                "token"
            )
        )
    }

    fun generateDummyRegister(): RegisterResponse {
        return RegisterResponse(
            false,
            "success"
        )
    }

    fun generateDummyAddStory(): PostStoryResponse{
        return PostStoryResponse(
            false,
            "success"
        )
    }

}