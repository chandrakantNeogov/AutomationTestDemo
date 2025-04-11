package com.neogov.automationtestdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val loginValidation = LoginValidation()

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> get() = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> get() = _passwordError

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    fun onLoginClicked(email: String, password: String) {
        var isValid = true

        if (!loginValidation.validateEmail(email)) {
            _emailError.value = "Invalid email"
            isValid = false
        }

        if (!loginValidation.validatePassword(password)) {
            _passwordError.value = "Invalid password"
            isValid = false
        }

        if (isValid) {
            _loginResult.value = "Login successful! Email: $email"
        }
    }

    fun clearEmailError() {
        _emailError.value = null
    }

    fun clearPasswordError() {
        _passwordError.value = null
    }

}