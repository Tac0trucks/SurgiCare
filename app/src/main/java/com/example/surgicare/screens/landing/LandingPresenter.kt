package com.example.surgicare.screens.landing

import com.example.surgicare.data.repository.PatientRepository
import com.example.surgicare.models.Role

class LandingPresenter(
    private val view: LandingContract.View,
    private val repository: PatientRepository
) : LandingContract.Presenter {

    override fun onRoleSelected(isPatient: Boolean) {
        val role = if (isPatient) Role.PATIENT else Role.DOCTOR

        repository.saveUserRole(role)

        if (isPatient) {
            view.startPatientFlow()
        } else {
            view.startDoctorFlow()
        }
    }
}