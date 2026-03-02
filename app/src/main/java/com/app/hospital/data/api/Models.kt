package com.app.hospital.data.api

// ─── Auth DTOs ────────────────────────────────────────────────────────────────

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val jwt: String,
    val userId: Long
)

data class SignupRequest(
    val username: String,
    val password: String,
    val name: String,
    val roles: List<String> = listOf("PATIENT")
)

data class SignupResponse(
    val message: String,
    val userId: Long? = null
)

// ─── Patient DTOs ─────────────────────────────────────────────────────────────

data class PatientProfile(
    val id: Long,
    val name: String,
    val username: String,
    val email: String? = null,
    val phone: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val address: String? = null,
    val bloodGroup: String? = null,
    val roles: List<String> = emptyList()
)

// ─── Generic API error wrapper ────────────────────────────────────────────────

data class ApiError(
    val message: String,
    val status: Int? = null,
    val error: String? = null
)
