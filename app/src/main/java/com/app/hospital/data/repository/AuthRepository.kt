package com.app.hospital.data.repository

import com.app.hospital.data.api.ApiService
import com.app.hospital.data.api.LoginRequest
import com.app.hospital.data.api.LoginResponse
import com.app.hospital.data.api.SignupRequest
import com.app.hospital.data.api.SignupResponse
import com.app.hospital.utils.NetworkResult
import com.app.hospital.utils.TokenManager
import com.app.hospital.utils.toNetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {

    suspend fun login(username: String, password: String): NetworkResult<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(username, password))
            val result = response.toNetworkResult()
            if (result is NetworkResult.Success) {
                tokenManager.saveToken(result.data.jwt, result.data.userId)
            }
            result
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun signup(
        username: String,
        password: String,
        name: String
    ): NetworkResult<SignupResponse> {
        return try {
            val response = apiService.signup(
                SignupRequest(username = username, password = password, name = name)
            )
            response.toNetworkResult()
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun logout() {
        tokenManager.clearToken()
    }

    suspend fun isLoggedIn() = tokenManager.isLoggedIn()
}
