package com.example.surgicare.screens.registration

interface RegisterContract {
    interface View {
        fun showRegistrationError(message: String)
        fun onRegistrationSuccess()
    }

    interface Presenter {
        fun validateAndRegister(
            name: String, age: String, sex: String,
            surgery: String, date: String, hospital: String, history: String
        )
    }
}