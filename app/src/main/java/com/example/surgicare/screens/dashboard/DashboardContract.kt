package com.example.surgicare.screens.dashboard

import com.example.surgicare.models.Patient

interface DashboardContract {
    interface View {
        fun displayPatientInfo(patient: Patient)
        fun updateWoundStatus(status: String, colorHex: String)
        fun navigateToSymptomCheck()
    }

    interface Presenter {
        fun loadDashboardData()
        fun onStartAssessmentClicked()
    }
}