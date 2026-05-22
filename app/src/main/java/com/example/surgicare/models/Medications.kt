package com.example.surgicare.models
import java.util.Date
data class Medication(
    val name: String,
    val dose: String,
    val frequency: String,
    var streakCount: Int = 0,
    var lastTakenDate: String? = null // Format: YYYY-MM-DD to prevent double-logging
)