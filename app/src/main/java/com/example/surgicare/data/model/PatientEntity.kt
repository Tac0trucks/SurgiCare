package com.example.surgicare.data.model

// Example using Room annotations for a local DB
// Folder: data/model/PatientEntity.kt
data class PatientEntity(
    val name: String?,
    val age: Int?,
    val surgeryType: String? = null
)