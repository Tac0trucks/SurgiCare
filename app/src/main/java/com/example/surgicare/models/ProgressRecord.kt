package com.example.surgicare.models

data class ProgressRecord(
    val dayNumber: Int,      // e.g., "Day 3"
    val date: String,        // "Oct 28"
    val statusText: String,  // "Normal Healing"
    val statusColor: String, // Hexcode for logic
    val painScore: Int       // 1-10
)