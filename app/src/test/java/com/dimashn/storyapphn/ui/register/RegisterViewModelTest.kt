package com.dimashn.storyapphn.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dimashn.storyapphn.data.Result
import com.dimashn.storyapphn.data.network.response.RegisterResponse
import com.dimashn.storyapphn.data.repository.UserRepository
import com.dimashn.storyapphn.util.DataDummy
import com.dimashn.storyapphn.util.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest{
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var registerViewModel: RegisterViewModel
    private val dummyRegister = DataDummy.generateDummyRegister()

    private val name = "Dimashn"
    private val email = "dimas@gmail.com"
    private val password = "12345"

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(userRepository)
    }

    @Test
    fun `when Login is Success`() {
        val expectedUser = MutableLiveData<Result<RegisterResponse>>()
        expectedUser.value = Result.Success(dummyRegister)
        Mockito.`when`(userRepository.userRegister(name, email, password)).thenReturn(expectedUser)

        val actualUser = registerViewModel.userRegister(name, email, password).getOrAwaitValue()

        Mockito.verify(userRepository).userRegister(name, email, password)
        assertNotNull(actualUser)
        assertTrue(actualUser is Result.Success)
    }

    @Test
    fun `when Login is Not Success`() {
        val expectedUser = MutableLiveData<Result<RegisterResponse>>()
        expectedUser.value = Result.Error("Login Failed")
        Mockito.`when`(userRepository.userRegister(name, email, password)).thenReturn(expectedUser)

        val actualUser = registerViewModel.userRegister(name, email, password).getOrAwaitValue()

        Mockito.verify(userRepository).userRegister(name, email, password)
        assertNotNull(actualUser)
        assertTrue(actualUser is Result.Error)
        assertEquals("Login Failed", (actualUser as Result.Error).error)
    }
}