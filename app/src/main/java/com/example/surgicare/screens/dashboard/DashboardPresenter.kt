package com.example.surgicare.screens.dashboard

import com.example.surgicare.data.repository.PatientRepository

class DashboardPresenter(
    private val view: DashboardContract.View,
    private val repository: PatientRepository
) : DashboardContract.Presenter {

    override fun loadDashboardData() {
        val patientModel = repository.getPatientProfile() // Assume this mapper function exists
        view.displayPatientInfo(patientModel)

        // Fetch wound status from data layer
        val status = patientModel.surgeryType // Placeholder logic
        view.updateWoundStatus("Pending Assessment", "#F59E0B")
    }

    override fun onStartAssessmentClicked() {
        view.navigateToSymptomCheck()
    }
}