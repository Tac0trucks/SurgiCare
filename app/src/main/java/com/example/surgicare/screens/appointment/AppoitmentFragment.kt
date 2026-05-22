package com.example.surgicare.screens.appointment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.surgicare.R
import com.example.surgicare.data.local.AppPreferenceManager
import com.example.surgicare.models.Appointment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class AppointmentFragment : Fragment(R.layout.fragment_schedule_appointment) {

    private var selectedType = "Teleconsult"
    private var selectedDate = "Today"
    private var selectedTime = "09:00 AM"

    // Theme Colors
    private val teal = "#009689"
    private val grayStroke = "#E2E8F0"
    private val white = "#FFFFFF"
    private val mintBg = "#F0FDFA"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardTele = view.findViewById<MaterialCardView>(R.id.cardTeleconsult)
        val cardClinic = view.findViewById<MaterialCardView>(R.id.cardClinic)
        val customDateContainer = view.findViewById<LinearLayout>(R.id.containerCustomDate)

        // 1. Consultation Type Logic
        cardTele.setOnClickListener {
            selectedType = "Teleconsult"
            updateCardSelection(cardTele, cardClinic)
        }

        cardClinic.setOnClickListener {
            selectedType = "Clinic Visit"
            updateCardSelection(cardClinic, cardTele)
        }

        // 2. Date Selection Logic
        val btnToday = view.findViewById<MaterialButton>(R.id.btnDateToday)
        val btnTomorrow = view.findViewById<MaterialButton>(R.id.btnDateTomorrow)
        val btnOther = view.findViewById<MaterialButton>(R.id.btnDateOther)

        btnToday.setOnClickListener {
            selectedDate = "Today"
            customDateContainer.visibility = View.GONE
        }
        btnTomorrow.setOnClickListener {
            selectedDate = "Tomorrow"
            customDateContainer.visibility = View.GONE
        }
        btnOther.setOnClickListener {
            customDateContainer.visibility = View.VISIBLE
        }

        // 3. Confirm Booking
        view.findViewById<MaterialButton>(R.id.btnConfirm).setOnClickListener {
            val appt = Appointment(selectedType, selectedDate, selectedTime)
            AppPreferenceManager(requireContext()).saveAppointment(appt)

            Toast.makeText(context, "Confirmed: $selectedType on $selectedDate", Toast.LENGTH_LONG).show()
            // Go back to Dashboard
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        view.findViewById<View>(R.id.btnBack)?.setOnClickListener {
            // Simply pop the fragment off the stack to go back to the Dashboard
            parentFragmentManager.popBackStack()
        }
    }

    private fun updateCardSelection(selected: MaterialCardView, unselected: MaterialCardView) {
        selected.setStrokeColor(Color.parseColor(teal))
        selected.setCardBackgroundColor(Color.parseColor(mintBg))

        unselected.setStrokeColor(Color.parseColor(grayStroke))
        unselected.setCardBackgroundColor(Color.parseColor(white))
    }
}