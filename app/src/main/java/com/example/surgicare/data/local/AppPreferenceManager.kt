package com.example.surgicare.data.local

import android.content.Context
import com.example.surgicare.models.Role


class AppPreferenceManager(context: Context) {
    private val sharedPrefs = context.getSharedPreferences("SurgiCare_Prefs", Context.MODE_PRIVATE)

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
}