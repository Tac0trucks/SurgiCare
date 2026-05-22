package com.example.surgicare.screens.registration

import com.example.surgicare.data.repository.PatientRepository
import com.example.surgicare.models.Patient

class RegisterPresenter(
    private var view: RegisterContract.View?,
    private val repository: PatientRepository
) : RegisterContract.Presenter {

    override fun register(
        name: String, age: String, sex: String,
        surgery: String, date: String, hospital: String, history: String
    ) {
        if (name.isBlank() || age.isBlank()) {
            view?.onRegistrationError("Please fill in required fields.")
            return
        }

        view?.showLoading()

        val patientModel = Patient(
            fullName = name,
            age = age.toInt(),
            sex = sex,
            surgeryType = surgery,
            surgeryDate = date,
            hospital = hospital,
            medicalHistory = history
        )

        val success = repository.registerPatient(patientModel)

        if (success) {
            view?.onRegistrationSuccess()
        } else {
            view?.onRegistrationError("Database saving failed")
        }
    }

    fun detachView() { view = null }
}