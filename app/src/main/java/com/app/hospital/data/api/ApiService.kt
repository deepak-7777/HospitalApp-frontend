package com.app.hospital.data.api

import com.app.hospital.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // AUTH
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<SignupResponse>


    // DOCTOR
    @GET("doctors")
    suspend fun getDoctors(): Response<List<Doctor>>

    @POST("doctors")
    suspend fun addDoctor(
        @Body doctor: Doctor
    ): Response<Doctor>


    // PATIENT
    @GET("patients")
    suspend fun getPatients(): Response<List<Patient>>

    @POST("patients")
    suspend fun addPatient(
        @Body patient: Patient
    ): Response<Patient>


    // APPOINTMENT
    @GET("appointments")
    suspend fun getAppointments(): Response<List<Appointment>>

    @POST("appointments")
    suspend fun bookAppointment(
        @Body appointment: Appointment
    ): Response<Appointment>


    // DEPARTMENT
    @GET("departments")
    suspend fun getDepartments(): Response<List<Department>>


    // INSURANCE
    @GET("insurance")
    suspend fun getInsurance(): Response<List<Insurance>>

}