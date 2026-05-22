package com.example.surgicare.screens.medications

import com.example.surgicare.data.repository.PatientRepository
import java.text.SimpleDateFormat
import java.util.*

class MedsPresenter(
    private val view: MedsContract.View,
    private val repository: PatientRepository
) : MedsContract.Presenter {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val today = dateFormat.format(Date())

    override fun loadMedicationStatus() {
        // Handle Amoxicillin
        val amoxStreak = repository.getStreak("Amoxicillin")
        val amoxTaken = repository.getLastTakenDate("Amoxicillin") == today
        view.updateStreakUI("Amoxicillin", amoxStreak, amoxTaken)

        // Handle Ibuprofen
        val ibuStreak = repository.getStreak("Ibuprofen")
        val ibuTaken = repository.getLastTakenDate("Ibuprofen") == today
        view.updateStreakUI("Ibuprofen", ibuStreak, ibuTaken)
    }

    override fun markAsTaken(medName: String) {
        val currentStreak = repository.getStreak(medName)
        val newStreak = currentStreak + 1

        repository.saveStreak(medName, newStreak)
        repository.saveLastTakenDate(medName, today)

        view.updateStreakUI(medName, newStreak, true)
        view.showSuccessMessage("Great job! $medName streak is now $newStreak 🔥")
    }
}