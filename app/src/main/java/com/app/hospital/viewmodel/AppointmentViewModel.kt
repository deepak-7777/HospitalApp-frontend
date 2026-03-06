package com.app.hospital.viewmodel

import androidx.lifecycle.*
import com.app.hospital.data.model.Appointment
import com.app.hospital.data.repository.AppointmentRepository
import com.app.hospital.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val repository: AppointmentRepository
) : ViewModel() {

    val appointments = MutableLiveData<List<Appointment>>()

    val error = MutableLiveData<String>()

    val loading = MutableLiveData<Boolean>()

    fun loadAppointments() {

        viewModelScope.launch {

            loading.value = true

            when (val result = repository.getAppointments()) {

                is NetworkResult.Success -> {

                    appointments.postValue(result.data)

                }

                is NetworkResult.Error -> {

                    error.postValue(result.message)

                }

                else -> {}

            }

            loading.value = false

        }
    }

    fun bookAppointment(
        patient: String,
        doctor: String,
        date: String,
        time: String
    ) {

        viewModelScope.launch {

            loading.value = true

            val appointment = Appointment(
                id = 0,
                patientName = patient,
                doctorName = doctor,
                date = date,
                time = time
            )

            when (repository.bookAppointment(appointment)) {

                is NetworkResult.Success -> {

                    loadAppointments()

                }

                is NetworkResult.Error -> {

                    error.postValue("Failed to book appointment")

                }

                else -> {}

            }

            loading.value = false

        }
    }
}