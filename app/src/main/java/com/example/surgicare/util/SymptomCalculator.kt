package com.example.surgicare.util


import com.example.surgicare.data.model.CheckupStatus


object SymptomCalculator {
    fun calculateWoundStatus(painScore: Int, symptoms: Map<String, Boolean>): CheckupStatus {
        var score = painScore * 0.5

        // Logical weights from your project plan (Simplified for now)
        if (symptoms["pus"] == true) score += 10.0
        if (symptoms["fever"] == true) score += 10.0

        return when {
            score >= 10.0 -> CheckupStatus.RED
            score >= 3.0 -> CheckupStatus.YELLOW
            else -> CheckupStatus.GREEN
        }
    }
}