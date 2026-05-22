package com.example.surgicare.screens.landing

interface LandingContract {
    interface View {
        fun startPatientFlow()
        fun startDoctorFlow()
    }
    interface Presenter {
        fun onRoleSelected(isPatient: Boolean)
    }
}