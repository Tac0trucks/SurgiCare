package com.example.surgicare.data.local

import android.content.Context
import com.example.surgicare.models.Role


class AppPreferenceManager(context: Context) {
    private val sharedPrefs = context.getSharedPreferences("SurgiCare_Prefs", Context.MODE_PRIVATE)

    fun saveRole(role: Role) {
        sharedPrefs.edit().putString("USER_ROLE", role.name).apply()
    }

    fun getRole(): String? = sharedPrefs.getString("USER_ROLE", null)

    fun savePatientName(name: String?) {
        sharedPrefs.edit().putString("PATIENT_NAME", name).apply()
    }

    fun getPatientName(): String? = sharedPrefs.getString("PATIENT_NAME", null)
}