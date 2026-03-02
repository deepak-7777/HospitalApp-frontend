package com.app.hospital.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hospital.data.api.LoginResponse
import com.app.hospital.data.api.SignupResponse
import com.app.hospital.data.repository.AuthRepository
import com.app.hospital.utils.NetworkResult
import com.app.hospital.utils.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<NetworkResult<LoginResponse>?>(null)
    val loginState: StateFlow<NetworkResult<LoginResponse>?> = _loginState

    private val _signupState = MutableStateFlow<NetworkResult<SignupResponse>?>(null)
    val signupState: StateFlow<NetworkResult<SignupResponse>?> = _signupState

    fun login(username: String, password: String) {
        // Validate
        if (!Validators.isValidUsername(username)) {
            _loginState.value = NetworkResult.Error("Username must be at least 3 characters")
            return
        }
        if (!Validators.isValidPassword(password)) {
            _loginState.value = NetworkResult.Error("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            _loginState.value = NetworkResult.Loading()
            _loginState.value = authRepository.login(username, password)
        }
    }

    fun signup(username: String, password: String, confirmPassword: String, name: String) {
        if (!Validators.isValidName(name)) {
            _signupState.value = NetworkResult.Error("Name must be at least 2 characters")
            return
        }
        if (!Validators.isValidUsername(username)) {
            _signupState.value = NetworkResult.Error("Username must be at least 3 characters")
            return
        }
        if (!Validators.isValidPassword(password)) {
            _signupState.value = NetworkResult.Error("Password must be at least 6 characters")
            return
        }
        if (!Validators.passwordsMatch(password, confirmPassword)) {
            _signupState.value = NetworkResult.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            _signupState.value = NetworkResult.Loading()
            _signupState.value = authRepository.signup(username, password, name)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun resetLoginState() { _loginState.value = null }
    fun resetSignupState() { _signupState.value = null }
}
