package com.example.surgicare.screens.assessment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.surgicare.R
import com.example.surgicare.data.repository.PatientRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class SymptomCheckFragment : Fragment(R.layout.fragment_symptom_check), AssessmentContract.View {

    private lateinit var presenter: AssessmentPresenter
    private val selectedSymptoms = mutableMapOf<String, Boolean>()

    // Hexcodes from your XML theme
    private val tealPrimary = "#009689"
    private val mintBackground = "#F0FDFA"
    private val grayStroke = "#F1F5F9"
    private val white = "#FFFFFF"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = AssessmentPresenter(this, PatientRepository(requireContext()))

        arguments?.getString("photoUri")?.let { uriString ->
            presenter.setPhoto(android.net.Uri.parse(uriString))
        }

        setupSymptomCards(view)

        view.findViewById<MaterialButton>(R.id.btnSubmit).setOnClickListener {
            presenter.setPainScore(1) // Placeholder, pain can be passed from Step 1
            presenter.submitAssessment()
        }

        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupSymptomCards(view: View) {
        // Mapping your XML Card IDs to internal keys
        val cards = listOf(
            view.findViewById<MaterialCardView>(R.id.cardSymptom1) to "abdominal_pain",
            view.findViewById<MaterialCardView>(R.id.cardSymptom2) to "nausea",
            view.findViewById<MaterialCardView>(R.id.cardSymptom3) to "bloating",
            view.findViewById<MaterialCardView>(R.id.cardSymptom4) to "fever",
            view.findViewById<MaterialCardView>(R.id.cardSymptom5) to "loss_appetite",
            view.findViewById<MaterialCardView>(R.id.cardSymptom6) to "constipation",
            view.findViewById<MaterialCardView>(R.id.cardSymptom7) to "difficulty_gas"
        )

        cards.forEach { (card, key) ->
            card.setOnClickListener {
                val isSelected = !(selectedSymptoms[key] ?: false)
                selectedSymptoms[key] = isSelected

                // Set colors using direct Hex strings
                if (isSelected) {
                    card.setStrokeColor(Color.parseColor(tealPrimary))
                    card.setCardBackgroundColor(Color.parseColor(mintBackground))
                } else {
                    card.setStrokeColor(Color.parseColor(grayStroke))
                    card.setCardBackgroundColor(Color.parseColor(white))
                }

                presenter.toggleSymptom(key, isSelected)
            }
        }
    }

    override fun showAnalysisResult(status: String, colorHex: String, advice: String) {
        // status is "Green", "Yellow", or "Red"
        // colorHex is the hex string from the logic engine

        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle(status)
        builder.setMessage(advice)
        builder.setPositiveButton("OK") { _, _ ->
            // Clear backstack and return Home
            parentFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        val dialog = builder.create()
        dialog.show()

        // Dynamic title color based on hexcodes
        val titleId = resources.getIdentifier("alertTitle", "id", "android")
        dialog.findViewById<android.widget.TextView>(titleId)?.setTextColor(Color.parseColor(colorHex))
    }

    // Boilerplate for Step 1
    override fun onPhotoCaptured(uri: android.net.Uri) {}
    override fun enableContinueButton(enabled: Boolean) {}
    override fun navigateToSymptoms() {}
}