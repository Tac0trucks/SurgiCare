package com.example.surgicare.screens.medications

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.surgicare.R
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.TimePickerDialog
import android.widget.LinearLayout
import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import java.util.Calendar
import com.example.surgicare.data.repository.PatientRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MedsFragment : Fragment(R.layout.fragment_medications), MedsContract.View {

    private lateinit var presenter: MedsPresenter

    private lateinit var adapter: MedsAdapter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(requireContext(), "Reminders won't work without notification permissions", Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MedsPresenter(this, PatientRepository(requireContext()))
        NotificationHelper.createNotificationChannel(requireContext())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        val rv = view.findViewById<RecyclerView>(R.id.rvMedications)
        rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = MedsAdapter(
            emptyList(), 
            onStreakClick = { name -> presenter.markAsTaken(name) },
            onFinishCourseClick = { name -> presenter.finishCourse(name) },
            onDeleteClick = { name -> presenter.deleteMedication(name) }
        )
        rv.adapter = adapter

        view.findViewById<FloatingActionButton>(R.id.fabAddMedication).setOnClickListener {
            showAddMedicationDialog()
        }

        presenter.loadMedicationStatus()
    }

    private fun showAddMedicationDialog() {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }
        
        val nameInput = EditText(requireContext()).apply {
            hint = "Enter medication name"
        }
        
        val dosageInput = EditText(requireContext()).apply {
            hint = "Dosage (e.g., 1 Tab)"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 20, 0, 0) }
        }
        
        layout.addView(nameInput)
        layout.addView(dosageInput)
        
        AlertDialog.Builder(requireContext())
            .setTitle("Add Medication")
            .setView(layout)
            .setPositiveButton("Next") { dialog: android.content.DialogInterface, _: Int ->
                val name = nameInput.text.toString()
                val dosage = dosageInput.text.toString()
                if (name.isNotBlank() && dosage.isNotBlank()) {
                    showTimePickerDialog(name, dosage)
                } else {
                    Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog: android.content.DialogInterface, _: Int ->
                dialog.cancel()
            }
            .show()
    }

    private fun showTimePickerDialog(medName: String, dosage: String) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            presenter.addMedication(medName, dosage, selectedHour, selectedMinute)
            NotificationHelper.scheduleMedicationReminder(requireContext(), medName, selectedHour, selectedMinute)
        }, hour, minute, false).apply {
            setTitle("Select Reminder Time")
        }.show()
    }

    override fun displayMedications(medications: List<MedicationStatus>) {
        adapter.updateData(medications)
    }

    override fun showSuccessMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showCongratulations(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("🎉 Congratulations!")
            .setMessage(message)
            .setPositiveButton("Awesome") { dialog: android.content.DialogInterface, _: Int -> dialog.dismiss() }
            .show()
    }
}