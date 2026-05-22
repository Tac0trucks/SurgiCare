package com.example.surgicare.data.repository

import android.content.Context
import com.example.surgicare.data.local.AppPreferenceManager
import com.example.surgicare.data.model.PatientEntity
import com.example.surgicare.models.Patient
import com.example.surgicare.models.Role

class PatientRepository(context: Context) {

    // 1. You must declare the prefManager here so the functions can see it
    private val prefManager = AppPreferenceManager(context)
    fun saveUserRole(role: Role) {
        prefManager.saveRole(role)
    }

    fun registerPatient(patient: Patient): Boolean {
        return try {
            // In a local app, prefManager is our "Data Model" orchestrator
            prefManager.savePatientProfile(
                patient.fullName,
                patient.age,
                patient.sex,
                patient.surgeryType,
                patient.surgeryDate,
                patient.hospital,
                patient.medicalHistory
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    // Additional helper function to check if the user is already logged in
    fun isProfileComplete(): Boolean {
        return prefManager.getPatientName() != null
    }

    fun getPatientProfile(): Patient {
        val name = prefManager.getPatientName() ?: "Valued Patient"
        // Convert Entity (Storage) back to Patient (Model)
        return Patient(fullName = name, age = 0, surgeryType = "Laparoscopic")
    }
}