package com.example.surgicare.data.repository

import android.content.Context
import com.example.surgicare.data.local.AppPreferenceManager
import com.example.surgicare.data.model.CheckupResult
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
        val age = prefManager.getPatientAge()
        val sex = prefManager.getPatientSex() ?: ""
        val surgeryType = prefManager.getPatientSurgery() ?: "Laparoscopic"
        val surgeryDate = prefManager.getPatientDate() ?: ""
        val hospital = prefManager.getPatientHospital() ?: ""
        val history = prefManager.getPatientHistory() ?: ""
        
        return Patient(
            fullName = name, 
            age = age, 
            sex = sex,
            surgeryType = surgeryType,
            surgeryDate = surgeryDate,
            hospital = hospital,
            medicalHistory = history
        )
    }

    fun clearData() {
        prefManager.clearAll()
    }


    fun getStreak(medName: String): Int {
        return prefManager.getMedicationStreak(medName)
    }

    fun saveStreak(medName: String, count: Int) {
        prefManager.saveMedicationStreak(medName, count)
    }

    fun getLastTakenDate(medName: String): String? {
        return prefManager.getLastTakenDate(medName)
    }

    fun saveLastTakenDate(medName: String, date: String) {
        prefManager.saveLastTakenDate(medName, date)
    }

    fun saveReminderTime(medName: String, timeStr: String) {
        prefManager.saveReminderTime(medName, timeStr)
    }

    fun getReminderTime(medName: String): String? {
        return prefManager.getReminderTime(medName)
    }
    fun saveAssessment(result: CheckupResult) {
        prefManager.addAssessmentToHistory(result)
    }

    fun getHealingHistory(): List<CheckupResult> {
        return prefManager.getHistoryList()
    }

    fun getMedicationsList(): List<String> {
        return prefManager.getMedicationsList()
    }

    fun addMedication(name: String) {
        prefManager.saveMedication(name)
    }
}