package com.example.surgicare.data.repository

import android.content.Context
import com.example.surgicare.data.local.AppPreferenceManager
import com.example.surgicare.data.model.PatientEntity
import com.example.surgicare.models.Patient

class PatientRepository(context: Context) {

    // 1. You must declare the prefManager here so the functions can see it
    private val prefManager = AppPreferenceManager(context)

    fun registerPatient(patient: Patient): Boolean {
        return try {
            // Mapping Domain Model -> Data Model
            val entity = PatientEntity(name = patient.fullName, age = patient.age)

            // 2. Now prefManager is resolved and will work
            prefManager.savePatientName(entity.name)

            // You can add more save lines here as you build out the PreferenceManager
            // prefManager.saveAge(entity.age)

            true
        } catch (e: Exception) {
            false
        }
    }

    // Additional helper function to check if the user is already logged in
    fun isProfileComplete(): Boolean {
        return prefManager.getPatientName() != null
    }
}