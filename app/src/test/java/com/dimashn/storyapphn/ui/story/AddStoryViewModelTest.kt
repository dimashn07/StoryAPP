package com.dimashn.storyapphn.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dimashn.storyapphn.data.network.response.PostStoryResponse
import com.dimashn.storyapphn.data.repository.StoryRepository
import com.dimashn.storyapphn.data.repository.UserRepository
import com.dimashn.storyapphn.util.DataDummy
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import com.dimashn.storyapphn.data.Result
import com.dimashn.storyapphn.util.getOrAwaitValue
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest{
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel
    private val dummyAddStory = DataDummy.generateDummyAddStory()
    private val token = "TOKEN"

    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(userRepository, storyRepository)
    }

    @Test
    fun `when Add Story is Success`() {
        val description = "description".toRequestBody("text/plain".toMediaType())
        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        val expectedStory = MutableLiveData<Result<PostStoryResponse>>()
        expectedStory.value = Result.Success(dummyAddStory)
        Mockito.`when`(storyRepository.addStory(token, imageMultipart, description)).thenReturn(expectedStory)

        val actualStory = addStoryViewModel.addStory(token, imageMultipart, description).getOrAwaitValue()

        Mockito.verify(storyRepository).addStory(token, imageMultipart, description)
        assertNotNull(actualStory)
        assertTrue(actualStory is Result.Success)
    }

    @Test
    fun `when Add Story is Not Success`() {
        val description = "description".toRequestBody("text/plain".toMediaType())
        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        val expectedStory = MutableLiveData<Result<PostStoryResponse>>()
        expectedStory.value = Result.Error("Failed to add story")
        Mockito.`when`(storyRepository.addStory(token, imageMultipart, description)).thenReturn(expectedStory)

        val actualStory = addStoryViewModel.addStory(token, imageMultipart, description).getOrAwaitValue()

        Mockito.verify(storyRepository).addStory(token, imageMultipart, description)
        assertNotNull(actualStory)
        assertTrue(actualStory is Result.Error)
        assertEquals("Failed to add story", (actualStory as Result.Error).error)
    }

}