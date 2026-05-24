package com.example.surgicare.screens.assessment

import com.example.surgicare.data.repository.PatientRepository
import com.example.surgicare.util.SymptomCalculator

class AssessmentPresenter(
    private val view: AssessmentContract.View,
    private val repository: PatientRepository
) : AssessmentContract.Presenter {

    private val currentCheck = mutableMapOf<String, Boolean>()
    private var currentPain = 1
    private var photoUriStr: String? = null

    override fun setPhoto(uri: android.net.Uri) { 
        photoUriStr = uri.toString()
    }

    override fun toggleSymptom(name: String, isChecked: Boolean) {
        currentCheck[name] = isChecked
    }

    override fun setPainScore(score: Int) {
        currentPain = score
    }

    override fun submitAssessment() {
        val baseResult = SymptomCalculator.analyze(currentPain, currentCheck)
        val result = baseResult.copy(photoUri = photoUriStr)

        // Save to Local History via Repository
        repository.saveAssessment(result)
        
        // Lock out photo uploads for the rest of today
        val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
        repository.saveLastPhotoUploadDate(today)

        view.showAnalysisResult(
            result.status.name,
            result.status.colorHex,
            result.instructions.joinToString("\n")
        )
    }
}