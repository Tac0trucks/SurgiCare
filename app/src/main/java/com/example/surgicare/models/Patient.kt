package com.example.surgicare.models

data class Patient(
    val fullName: String,
    val age: Int,
    val sex: String = "",
    val surgeryType: String = "",
    val surgeryDate: String = "",
    val hospital: String = "",
    val medicalHistory: String = "None"
)