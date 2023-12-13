package com.dimashn.storyapphn.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dimashn.storyapphn.data.network.response.LoginResponse
import com.dimashn.storyapphn.data.repository.UserRepository
import com.dimashn.storyapphn.util.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import com.dimashn.storyapphn.data.Result
import com.dimashn.storyapphn.util.getOrAwaitValue
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest{
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLogin = DataDummy.generateDummyLogin()

    private val email = "dimas@gmail.com"
    private val password = "12345"

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(userRepository)
    }

    @Test
    fun `when Login is Success`() {
        val expectedUser = MutableLiveData<Result<LoginResponse>>()
        expectedUser.value = Result.Success(dummyLogin)
        Mockito.`when`(userRepository.userLogin(email, password)).thenReturn(expectedUser)

        val actualUser = loginViewModel.userLogin(email, password).getOrAwaitValue()

        Mockito.verify(userRepository).userLogin(email, password)
        assertNotNull(actualUser)
        assertTrue(actualUser is Result.Success)
    }

    @Test
    fun `when Login is Not Success`() {
        val expectedUser = MutableLiveData<Result<LoginResponse>>()
        expectedUser.value = Result.Error("Login Failed")
        Mockito.`when`(userRepository.userLogin(email, password)).thenReturn(expectedUser)

        val actualUser = loginViewModel.userLogin(email, password).getOrAwaitValue()

        Mockito.verify(userRepository).userLogin(email, password)
        assertNotNull(actualUser)
        assertTrue(actualUser is Result.Error)
        assertEquals("Login Failed", (actualUser as Result.Error).error)
    }

}