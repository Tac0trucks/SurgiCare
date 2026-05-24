package com.example.surgicare.screens.profile

import com.example.surgicare.data.repository.PatientRepository
import com.example.surgicare.models.Patient

class ProfilePresenter(
    private val view: ProfileContract.View,
    private val repository: PatientRepository
) : ProfileContract.Presenter {

    override fun loadProfile() {
        val patient = repository.getPatientProfile()
        view.showProfile(patient)
    }

    override fun logout() {
        repository.setLoggedIn(false)
        view.navigateToLogin()
    }
}
