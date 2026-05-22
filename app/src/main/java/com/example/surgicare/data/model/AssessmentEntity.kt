package com.example.surgicare.data.model


data class AssessmentEntity(
    val date: Long,
    val painScore: Int,
    val symptomsBitmask: Int, // Compact way to store 17 symptoms
    val imagePath: String?
)