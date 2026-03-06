package com.app.hospital.data.model

data class Appointment(
    val id: Long,
    val patientName: String,
    val doctorName: String,
    val date: String,
    val time: String
)
