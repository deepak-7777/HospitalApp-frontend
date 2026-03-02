package com.app.hospital.data.repository

import com.app.hospital.data.api.ApiService
import com.app.hospital.data.api.PatientProfile
import com.app.hospital.utils.NetworkResult
import com.app.hospital.utils.toNetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getPatientProfile(): NetworkResult<PatientProfile> {
        return try {
            val response = apiService.getPatientProfile()
            response.toNetworkResult()
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Network error occurred")
        }
    }
}
