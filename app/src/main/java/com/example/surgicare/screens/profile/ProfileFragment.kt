package com.example.surgicare.screens.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.surgicare.R
import com.example.surgicare.data.repository.PatientRepository
import com.example.surgicare.models.Patient
import com.example.surgicare.screens.landing.LandingActivity

class ProfileFragment : Fragment(R.layout.fragment_profile), ProfileContract.View {

    private lateinit var presenter: ProfilePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        presenter = ProfilePresenter(this, PatientRepository(requireContext()))
        presenter.loadProfile()

        view.findViewById<AppCompatButton>(R.id.btnLogout).setOnClickListener {
            presenter.logout()
        }
    }

    override fun showProfile(patient: Patient) {
        view?.apply {
            val tvProfileName = findViewById<TextView>(R.id.tvProfileName)
            val tvAvatarInitials = findViewById<TextView>(R.id.tvAvatarInitials)
            val tvSurgeryType = findViewById<TextView>(R.id.tvSurgeryType)
            val tvSurgeryDate = findViewById<TextView>(R.id.tvSurgeryDate)

            tvProfileName.text = patient.fullName
            
            // Generate initials
            val initials = patient.fullName.split(" ")
                .filter { it.isNotEmpty() }
                .take(2)
                .map { it[0].uppercaseChar() }
                .joinToString("")
            tvAvatarInitials.text = if (initials.isNotEmpty()) initials else "P"

            tvSurgeryType.text = patient.surgeryType
            tvSurgeryDate.text = patient.surgeryDate
        }
    }

    override fun navigateToLogin() {
        val intent = Intent(requireContext(), LandingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}
