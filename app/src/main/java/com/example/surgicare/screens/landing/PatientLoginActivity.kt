package com.example.surgicare.screens.landing

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.surgicare.MainActivity
import com.example.surgicare.R
import com.example.surgicare.data.repository.PatientRepository

class PatientLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        
        val repository = PatientRepository(this)

        btnLogin.setOnClickListener {
            val name = etFullName.text.toString()
            val password = etPassword.text.toString()

            if (name.isNotBlank() && password.isNotBlank()) {
                val savedName = repository.getPatientProfile().fullName
                // In demo, check if name matches the saved one (or "Valued Patient" if default)
                if (repository.isProfileComplete() && name.equals(savedName, ignoreCase = true)) {
                    repository.setLoggedIn(true)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Account not found or name mismatch", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter name and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
