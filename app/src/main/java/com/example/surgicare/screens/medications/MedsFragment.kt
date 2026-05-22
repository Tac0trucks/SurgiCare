package com.example.surgicare.screens.medications

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.surgicare.R
import com.example.surgicare.data.repository.PatientRepository

class MedsFragment : Fragment(R.layout.fragment_medications), MedsContract.View {

    private lateinit var presenter: MedsPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MedsPresenter(this, PatientRepository(requireContext()))

        // Setup Buttons
        view.findViewById<AppCompatButton>(R.id.btnStreakAmoxicillin).setOnClickListener {
            presenter.markAsTaken("Amoxicillin")
        }

        view.findViewById<AppCompatButton>(R.id.btnStreakIbuprofen).setOnClickListener {
            presenter.markAsTaken("Ibuprofen")
        }

        presenter.loadMedicationStatus()
    }

    override fun updateStreakUI(medName: String, streak: Int, isTakenToday: Boolean) {
        val streakTextViewId = if (medName == "Amoxicillin") R.id.tvStreakAmoxicillin else R.id.tvStreakIbuprofen
        val buttonId = if (medName == "Amoxicillin") R.id.btnStreakAmoxicillin else R.id.btnStreakIbuprofen

        val tvStreak = view?.findViewById<TextView>(streakTextViewId)
        val btn = view?.findViewById<AppCompatButton>(buttonId)

        tvStreak?.text = "$streak 🔥"

        if (isTakenToday) {
            btn?.isEnabled = false
            btn?.text = "Taken"
            btn?.alpha = 0.5f
        }
    }

    override fun showSuccessMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}