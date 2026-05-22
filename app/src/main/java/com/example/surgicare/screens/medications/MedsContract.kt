package com.example.surgicare.screens.medications

interface MedsContract {
    interface View {
        fun updateStreakUI(medName: String, streak: Int, isTakenToday: Boolean)
        fun showSuccessMessage(message: String)
    }

    interface Presenter {
        fun loadMedicationStatus()
        fun markAsTaken(medName: String)
    }
}