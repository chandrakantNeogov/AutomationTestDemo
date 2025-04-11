package com.neogov.automationtestdemo

import org.junit.Test

class LoginValidationTest {
    @Test
    fun validateEmail_withValidEmail_returnsTrue() {
        val validation = LoginValidation()
        val result = validation.validateEmail("test@example.com")
        assert(result)
    }

    @Test
    fun validateEmail_withInvalidEmail_returnsFalse() {
        val validation = LoginValidation()
        val result = validation.validateEmail("invalid-email")
        assert(!result)
    }

    @Test
    fun validateEmail_withEmptyEmail_returnsFalse() {
        val validation = LoginValidation()
        val result = validation.validateEmail("")
        assert(!result)
    }

    @Test
    fun validateEmail_withNullEmail_returnsFalse() {
        val validation = LoginValidation()
        val result = validation.validateEmail(null)
        assert(!result)
    }

    // validatePassword
    @Test
    fun validatePassword_withValidPassword_returnsTrue() {
        val validation = LoginValidation()
        val result = validation.validatePassword("validpassword")
        assert(result)
    }

    @Test
    fun validatePassword_withShortPassword_returnsFalse() {
        val validation = LoginValidation()
        val result = validation.validatePassword("short")
        assert(!result)
    }

    @Test
    fun validatePassword_withEmptyPassword_returnsFalse() {
        val validation = LoginValidation()
        val result = validation.validatePassword("")
        assert(!result)
    }
}