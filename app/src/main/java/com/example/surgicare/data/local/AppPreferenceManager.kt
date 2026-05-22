package com.example.surgicare.data.local

import android.content.Context
import com.example.surgicare.models.Appointment
import com.example.surgicare.data.model.CheckupResult
import com.example.surgicare.models.Role
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class AppPreferenceManager(context: Context) {
    private val sharedPrefs = context.getSharedPreferences("SurgiCare_Prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    fun savePatientProfile(name: String, age: Int, sex: String, surgery: String, date: String, hospital: String, history: String) {
        sharedPrefs.edit().apply {
            putString("NAME", name)
            putInt("AGE", age)
            putString("SEX", sex)
            putString("SURGERY", surgery)
            putString("DATE", date)
            putString("HOSPITAL", hospital)
            putString("HISTORY", history)
            putBoolean("PROFILE_COMPLETE", true)
        }.apply()
    }

    fun isProfileComplete(): Boolean = sharedPrefs.getBoolean("PROFILE_COMPLETE", false)
    fun getPatientName(): String? = sharedPrefs.getString("NAME", null)
    fun getPatientAge(): Int = sharedPrefs.getInt("AGE", 0)
    fun getPatientSex(): String? = sharedPrefs.getString("SEX", null)
    fun getPatientSurgery(): String? = sharedPrefs.getString("SURGERY", null)
    fun getPatientDate(): String? = sharedPrefs.getString("DATE", null)
    fun getPatientHospital(): String? = sharedPrefs.getString("HOSPITAL", null)
    fun getPatientHistory(): String? = sharedPrefs.getString("HISTORY", null)

    fun clearAll() {
        sharedPrefs.edit().clear().apply()
    }
    fun saveRole(role: Role) {

    }
    fun saveMedicationStreak(medName: String, count: Int) {
        sharedPrefs.edit().putInt("STREAK_$medName", count).apply()
    }

    fun getMedicationStreak(medName: String): Int = sharedPrefs.getInt("STREAK_$medName", 0)

    fun saveLastTakenDate(medName: String, date: String) {
        sharedPrefs.edit().putString("DATE_$medName", date).apply()
    }

    fun getLastTakenDate(medName: String): String? = sharedPrefs.getString("DATE_$medName", null)
    fun addAssessmentToHistory(newRecord: CheckupResult) {
        val currentHistory = getHistoryList().toMutableList()
        currentHistory.add(newRecord)

        // Convert List -> String
        val jsonString = gson.toJson(currentHistory)
        sharedPrefs.edit().putString("HISTORY_LIST", jsonString).apply()
    }

    // 2. Logic to RETRIEVE the full history list
    fun getHistoryList(): List<CheckupResult> {
        val jsonString = sharedPrefs.getString("HISTORY_LIST", null) ?: return emptyList()

        // Convert String -> List
        val type = object : TypeToken<List<CheckupResult>>() {}.type
        return gson.fromJson(jsonString, type)
    }
    fun saveAppointment(appt: Appointment) {
        val json = gson.toJson(appt)
        sharedPrefs.edit().putString("UPCOMING_APPT", json).apply()
    }

    fun getUpcomingAppointment(): Appointment? {
        val json = sharedPrefs.getString("UPCOMING_APPT", null) ?: return null
        return gson.fromJson(json, Appointment::class.java)
    }
}