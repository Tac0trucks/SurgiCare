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
            val reminderTime = repository.getReminderTime(medName)
            MedicationStatus(medName, streak, isTakenToday, reminderTime)
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

    override fun addMedication(medName: String, hour: Int, minute: Int) {
        val name = medName.trim()
        if (name.isNotEmpty()) {
            val medsList = repository.getMedicationsList()
            if (medsList.any { it.equals(name, ignoreCase = true) }) {
                view.showSuccessMessage("$name is already in your list.")
            } else {
                repository.addMedication(name)
                
                val timeStr = String.format("%02d:%02d", hour, minute)
                repository.saveReminderTime(name, timeStr)
                
                loadMedicationStatus()
                view.showSuccessMessage("Added $name with reminder at $timeStr")
            }
        }
    }
}