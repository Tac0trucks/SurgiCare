package com.example.surgicare.util


import com.example.surgicare.data.model.CheckupResult
import com.example.surgicare.data.model.CheckupStatus


object SymptomCalculator {
        fun analyze(painScore: Int, symptoms: Map<String, Boolean>): CheckupResult {
        var score = painScore * 0.5

        // RED FLAG Symptoms (Sec 4.3)
        if (symptoms["fever"] == true) score += 10.0
        if (symptoms["pus"] == true) score += 10.0
        if (symptoms["bleeding"] == true) score += 10.0
        if (symptoms["breathing"] == true) score += 10.0

        // YELLOW FLAG Symptoms
        if (symptoms["nausea"] == true) score += 2.0
        if (symptoms["bloating"] == true) score += 2.0
        if (symptoms["difficulty_gas"] == true) score += 2.0

        return when {
            score >= 10.0 -> CheckupResult(CheckupStatus.RED, score, "#EF4444", listOf("Bullet point 1", "Bullet point 2"))
            score >= 3.0 -> CheckupResult(CheckupStatus.YELLOW, score, "#F59E0B", listOf("Bullet point 1", "Bullet point 2"))
            else -> CheckupResult(CheckupStatus.GREEN, score, "#22C55E", listOf("Bullet point 1", "Bullet point 2"))
        }
    }
}