package com.example.surgicare.screens.medications

data class MedicationStatus(
    val name: String,
    val streak: Int,
    val isTakenToday: Boolean,
    val reminderTime: String?,
    val dosage: String?
)
