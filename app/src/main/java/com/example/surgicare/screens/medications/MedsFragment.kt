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
import com.example.surgicare.data.repository.PatientRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MedsFragment : Fragment(R.layout.fragment_medications), MedsContract.View {

    private lateinit var presenter: MedsPresenter

    private lateinit var adapter: MedsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MedsPresenter(this, PatientRepository(requireContext()))

        val rv = view.findViewById<RecyclerView>(R.id.rvMedications)
        rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = MedsAdapter(emptyList()) { medName ->
            presenter.markAsTaken(medName)
        }
        rv.adapter = adapter

        view.findViewById<FloatingActionButton>(R.id.fabAddMedication).setOnClickListener {
            showAddMedicationDialog()
        }

        presenter.loadMedicationStatus()
    }

    private fun showAddMedicationDialog() {
        val input = EditText(requireContext())
        input.hint = "Enter medication name"
        
        AlertDialog.Builder(requireContext())
            .setTitle("Add Medication")
            .setView(input)
            .setPositiveButton("Add") { dialog, _ ->
                val name = input.text.toString()
                if (name.isNotBlank()) {
                    presenter.addMedication(name)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    override fun displayMedications(medications: List<MedicationStatus>) {
        adapter.updateData(medications)
    }

    override fun showSuccessMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}