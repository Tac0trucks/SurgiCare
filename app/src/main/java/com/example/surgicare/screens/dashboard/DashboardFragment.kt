package com.example.surgicare.screens.dashboard

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.surgicare.MainActivity
import com.example.surgicare.R
import com.example.surgicare.data.repository.PatientRepository
import com.example.surgicare.models.Patient
import com.example.surgicare.screens.appointment.AppointmentFragment
import com.example.surgicare.screens.assessment.UploadPhotoFragment

class DashboardFragment : Fragment(R.layout.fragment_patient_dashboard), DashboardContract.View {

    private lateinit var presenter: DashboardPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = PatientRepository(requireContext())
        presenter = DashboardPresenter(this, repository)

        // Find Dashboard items from XML
        val btnStart = view.findViewById<View>(R.id.btnStartAssessment) // Add this ID to the "Start" button in XML

        btnStart?.setOnClickListener {
            presenter.onStartAssessmentClicked()
        }

        presenter.loadDashboardData()
        val cardAssessment = view.findViewById<View>(R.id.cardDailyAssessment)
        val cardProgress = view.findViewById<View>(R.id.cardHealingProgress)
        val cardMeds = view.findViewById<View>(R.id.cardMedications)
        val cardAppointments = view.findViewById<View>(R.id.cardAppointment)
        cardAssessment.setOnClickListener {
            // Jump to the Assessment Flow
            navigateToSymptomCheck()
        }
        cardProgress.setOnClickListener {
            // This looks for the MainActivity and calls our new helper
            (activity as? MainActivity)?.navigateToTab(R.id.nav_progress)
        }

        cardMeds.setOnClickListener {
            (activity as? MainActivity)?.navigateToTab(R.id.nav_meds)
        }
        cardAppointments.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, AppointmentFragment())
                .addToBackStack(null) // This allows the user to click the "Back" button to return to the Dashboard
                .commit()
        }
    }

    override fun displayPatientInfo(patient: Patient) {
        view?.findViewById<TextView>(R.id.tvWelcomeName)?.text = patient.fullName
    }

    override fun updateWoundStatus(status: String, colorHex: String) {
        // Code to update the status text and tint the status icon/card
    }

    override fun navigateToSymptomCheck() {

            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, UploadPhotoFragment())
                .addToBackStack(null)
                .commit()

    }
}