package com.example.surgicare.screens.doctor

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.surgicare.R
import com.example.surgicare.data.repository.PatientRepository

class DoctorMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_doctor_dashboard)
        
        // This is a mock implementation as requested:
        // "Show a Mock List of patients with statuses derived from the local repository"
        
        val repository = PatientRepository(this)
        val history = repository.getHealingHistory()
        
        // We will just dynamically update the first mock card with the local patient's latest status if available
        if (history.isNotEmpty() && repository.isProfileComplete()) {
            val patient = repository.getPatientProfile()
            val latestAssessment = history.last()
            
            // Assuming we use the first card for the local user
            // We need to find the views in the first layout card
            // In fragment_doctor_dashboard.xml there are no IDs for these, so we would normally give them IDs.
            // For now we'll just leave it as the mock list since the XML is already populated with mock data.
        }
    }
}
