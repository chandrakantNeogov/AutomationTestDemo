package com.neogov.automationtestdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun onLoginClicked_withValidEmailAndPassword_setsLoginResult() {
        val viewModel = MainViewModel()
        viewModel.onLoginClicked("test@example.com", "validpassword")
        assertEquals("Login successful! Email: test@example.com", viewModel.loginResult.value)
    }

    @Test
    fun onLoginClicked_withInvalidEmail_setsEmailError() {
        val viewModel = MainViewModel()
        viewModel.onLoginClicked("invalid-email", "validpassword")
        assertEquals("Invalid email", viewModel.emailError.value)
        assertNull(viewModel.loginResult.value)
    }

    @Test
    fun onLoginClicked_withInvalidPassword_setsPasswordError() {
        val viewModel = MainViewModel()
        viewModel.onLoginClicked("test@example.com", "short")
        assertEquals("Invalid password", viewModel.passwordError.value)
        assertNull(viewModel.loginResult.value)
    }

    @Test
    fun onLoginClicked_withInvalidEmailAndPassword_setsBothErrors() {
        val viewModel = MainViewModel()
        viewModel.onLoginClicked("invalid-email", "short")
        assertEquals("Invalid email", viewModel.emailError.value)
        assertEquals("Invalid password", viewModel.passwordError.value)
        assertNull(viewModel.loginResult.value)
    }

    @Test
    fun clearEmailError_clearsEmailError() {
        val viewModel = MainViewModel()
        viewModel.onLoginClicked("invalid-email", "validpassword")
        viewModel.clearEmailError()
        assertNull(viewModel.emailError.value)
    }

    @Test
    fun clearPasswordError_clearsPasswordError() {
        val viewModel = MainViewModel()
        viewModel.onLoginClicked("test@example.com", "short")
        viewModel.clearPasswordError()
        assertNotNull(viewModel.passwordError.value)
    }
}