package com.example.surgicare.screens.registration

interface RegisterContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun onRegistrationSuccess()
        fun onRegistrationError(message: String)
    }

    interface Presenter {
        fun register(
            name: String, age: String, sex: String,
            surgery: String, date: String, hospital: String, history: String
        )
    }
}