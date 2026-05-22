package com.example.surgicare.util


import com.example.surgicare.data.model.CheckupResult
import com.example.surgicare.data.model.CheckupStatus


object SymptomCalculator {
    fun analyze(painScore: Int, symptoms: Map<String, Boolean>): CheckupResult {
        var score = painScore * 0.5

        // Critical Logic for the Test:
        // Checking "Fever" or "Nausea" will trigger high scores
        if (symptoms["fever"] == true) score += 10.0
        if (symptoms["nausea"] == true) score += 3.0

        return when {
            score >= 10.0 -> CheckupResult(
                CheckupStatus.RED,
                score,
                "#EF4444", // Emergenxy Red
                listOf("🔴 Seek immediate medical attention", "🔴 Contact surgeon immediately")
            )
            score >= 3.0 -> CheckupResult(
                CheckupStatus.YELLOW,
                score,
                "#F59E0B", // Warning Orange
                listOf("🟡 Reassess symptoms regularly", "🟡 Notify healthcare provider")
            )
            else -> CheckupResult(
                CheckupStatus.GREEN,
                score,
                "#22C55E", // Healing Green
                listOf("🟢 Continue light movement", "🟢 Keep incision clean and dry")
            )
        }
    }
}