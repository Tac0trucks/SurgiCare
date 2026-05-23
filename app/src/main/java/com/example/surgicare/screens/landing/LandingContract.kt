package com.example.surgicare.screens.landing

interface LandingContract {
    interface View {
        fun startPatientFlow()
        fun startDoctorFlow()
        fun startMainActivity()
    }
    interface Presenter {
        fun onRoleSelected(isPatient: Boolean)
        fun checkSession()
    }
}