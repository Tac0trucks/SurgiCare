package com.example.surgicare.screens.profile

import com.example.surgicare.models.Patient

interface ProfileContract {
    interface View {
        fun showProfile(patient: Patient)
        fun navigateToLogin()
    }

    interface Presenter {
        fun loadProfile()
        fun logout()
    }
}
