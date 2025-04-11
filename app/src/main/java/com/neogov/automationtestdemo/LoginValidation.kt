package com.neogov.automationtestdemo


class LoginValidation {
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

    fun validateEmail(email: String?): Boolean {
        if (email.isNullOrEmpty()) return false
        return emailRegex.matches(email)
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }
}