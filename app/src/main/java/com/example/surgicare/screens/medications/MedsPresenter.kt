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
        val medsList = repository.getMedicationsList()
        val statusList = medsList.map { medName ->
            val streak = repository.getStreak(medName)
            val isTakenToday = repository.getLastTakenDate(medName) == today
            MedicationStatus(medName, streak, isTakenToday)
        }
        view.displayMedications(statusList)
    }

    override fun markAsTaken(medName: String) {
        val currentStreak = repository.getStreak(medName)
        val newStreak = currentStreak + 1

        repository.saveStreak(medName, newStreak)
        repository.saveLastTakenDate(medName, today)

        view.showSuccessMessage("Great job! $medName streak is now $newStreak 🔥")
        loadMedicationStatus()
    }

    override fun addMedication(medName: String) {
        val name = medName.trim()
        if (name.isNotEmpty()) {
            val medsList = repository.getMedicationsList()
            if (medsList.any { it.equals(name, ignoreCase = true) }) {
                view.showSuccessMessage("$name is already in your list.")
            } else {
                repository.addMedication(name)
                loadMedicationStatus()
                view.showSuccessMessage("Added $name")
            }
        }
    }
}