package com.example.surgicare.screens.assessment

import android.net.Uri

interface AssessmentContract {
    interface View {
        fun onPhotoCaptured(uri: Uri)
        fun enableContinueButton(enabled: Boolean)
        fun navigateToSymptoms()
        fun showAnalysisResult(status: String, color: String, advice: String)
    }

    interface Presenter {
        fun setPhoto(uri: Uri)
        fun toggleSymptom(name: String, isChecked: Boolean)
        fun setPainScore(score: Int)
        fun submitAssessment()
    }
}