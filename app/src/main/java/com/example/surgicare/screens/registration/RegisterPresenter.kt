package com.example.surgicare.screens.registration

import com.example.surgicare.data.repository.PatientRepository
import com.example.surgicare.models.Patient

class RegisterPresenter(
    private val view: RegisterContract.View,
    private val repository: PatientRepository
) : RegisterContract.Presenter {

    override fun validateAndRegister(
        name: String, age: String, sex: String,
        surgery: String, date: String, hospital: String, history: String
    ) {
        // Simple Validation logic
        if (name.isEmpty() || age.isEmpty() || surgery.isEmpty() || hospital.isEmpty()) {
            view.showRegistrationError("Please fill in all mandatory fields")
            return
        }

        // Map UI data to Model and Save
        val patient = Patient(name, age.toInt(), sex, surgery, date, hospital, history)
        val success = repository.registerPatient(patient)

        if (success) {
            view.onRegistrationSuccess()
        } else {
            view.showRegistrationError("Saving failed. Please try again.")
        }
    }
}