package com.app.hospital.data.repository

import com.app.hospital.data.api.ApiService
import com.app.hospital.data.model.Appointment
import com.app.hospital.utils.NetworkResult
import com.app.hospital.utils.toNetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppointmentRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getAppointments(): NetworkResult<List<Appointment>> {

        return try {

            val response = api.getAppointments()

            response.toNetworkResult()

        } catch (e: Exception) {

            NetworkResult.Error(
                e.message ?: "Unable to fetch appointments"
            )

        }
    }

    suspend fun bookAppointment(
        appointment: Appointment
    ): NetworkResult<Appointment> {

        return try {

            val response = api.bookAppointment(appointment)

            response.toNetworkResult()

        } catch (e: Exception) {

            NetworkResult.Error(
                e.message ?: "Unable to book appointment"
            )

        }
    }

}