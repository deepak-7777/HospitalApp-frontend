package com.app.hospital.utils

object Validators {

    fun isValidUsername(username: String): Boolean {
        return username.isNotBlank() && username.length >= 3
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotBlank() && password.length >= 6
    }

    fun isValidName(name: String): Boolean {
        return name.isNotBlank() && name.length >= 2
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun passwordsMatch(password: String, confirm: String): Boolean {
        return password == confirm
    }
}
