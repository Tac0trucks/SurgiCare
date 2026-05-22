package com.example.surgicare.screens.registration

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.surgicare.MainActivity
import com.example.surgicare.R
import com.example.surgicare.data.repository.PatientRepository
import com.example.surgicare.util.Constants
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class PatientRegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_register)

        presenter = RegisterPresenter(this, PatientRepository(this))

        setupDropdowns()
        setupDatePicker()

        findViewById<MaterialButton>(R.id.btnCompleteRegistration).setOnClickListener {
            registerPatient()
        }
    }

    private fun setupDropdowns() {
        // 1. Sex Dropdown
        val sexOptions = arrayOf("Male", "Female", "Other")
        val sexAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, sexOptions)
        findViewById<AutoCompleteTextView>(R.id.actvSex).setAdapter(sexAdapter)

        // 2. Surgery Type Dropdown (From our Constants file)
        val surgeryAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, Constants.SURGERY_OPTIONS)
        findViewById<AutoCompleteTextView>(R.id.actvSurgeryType).setAdapter(surgeryAdapter)
    }

    private fun setupDatePicker() {
        val etSurgeryDate = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etDate)

        etSurgeryDate.setOnClickListener {
            val calendar = java.util.Calendar.getInstance()
            val year = calendar.get(java.util.Calendar.YEAR)
            val month = calendar.get(java.util.Calendar.MONTH)
            val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

            val datePickerDialog = android.app.DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Format: YYYY-MM-DD
                    val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                    etSurgeryDate.setText(formattedDate)
                },
                year, month, day
            )

            datePickerDialog.show()
        }
    }

    private fun registerPatient() {
        val name = findViewById<TextInputEditText>(R.id.etFullName).text.toString()
        val age = findViewById<TextInputEditText>(R.id.etAge).text.toString()
        val sex = findViewById<AutoCompleteTextView>(R.id.actvSex).text.toString()
        val surgery = findViewById<AutoCompleteTextView>(R.id.actvSurgeryType).text.toString()
        val date = findViewById<TextInputEditText>(R.id.etDate).text.toString()
        val hospital = findViewById<TextInputEditText>(R.id.etHospital).text.toString()
        val history = findViewById<TextInputEditText>(R.id.etHistory).text.toString()

        presenter.validateAndRegister(name, age, sex, surgery, date, hospital, history)
    }

    override fun showRegistrationError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onRegistrationSuccess() {
        // Navigation to Main Portal
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}