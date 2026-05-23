package com.example.surgicare.screens.medications

interface MedsContract {
    interface View {
        fun displayMedications(medications: List<MedicationStatus>)
        fun showSuccessMessage(message: String)
    }

    interface Presenter {
        fun loadMedicationStatus()
        fun markAsTaken(medName: String)
        fun addMedication(medName: String)
    }
}