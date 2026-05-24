package com.example.surgicare.data.model

enum class CheckupStatus(val colorHex: String) {
    GREEN("#22c55e"),
    YELLOW("#f59e0b"),
    RED("#ef4444")
}

data class CheckupResult(
    val status: CheckupStatus,
    val score: Double,
    val colorHex: String,
    val instructions: List<String>,
    val photoUri: String? = null
)