package com.example.surgicare.models

data class Appointment(
    val type: String, // "Teleconsult" or "Clinic"
    val date: String,
    val time: String,
    val provider: String = "Dr. Sarah Johnson"
)