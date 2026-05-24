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
            if (repository.isProfileComplete()) {
                repository.setLoggedIn(true)
                view.startMainActivity()
            } else {
                view.startPatientFlow()
            }
        } else {
            view.startDoctorFlow()
        }
    }

    override fun checkSession() {
        if (repository.isLoggedIn() && repository.isProfileComplete()) {
            view.startMainActivity()
        }
    }
}