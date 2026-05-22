package com.example.surgicare.screens.dashboard

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.surgicare.R
import com.example.surgicare.data.repository.PatientRepository
import com.example.surgicare.models.Patient

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
    }

    override fun displayPatientInfo(patient: Patient) {
        view?.findViewById<TextView>(R.id.tvWelcomeName)?.text = patient.fullName
    }

    override fun updateWoundStatus(status: String, colorHex: String) {
        // Code to update the status text and tint the status icon/card
    }

    override fun navigateToSymptomCheck() {
        // Fragment transaction to move to UploadPhotoFragment
    }
}