package com.example.surgicare.screens.progress

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surgicare.R
import com.example.surgicare.data.model.CheckupResult
import com.example.surgicare.data.model.CheckupStatus
import com.example.surgicare.data.repository.PatientRepository

class ProgressFragment : Fragment(R.layout.fragment_progress) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = PatientRepository(requireContext())
        var history = repository.getHealingHistory()
        // FOR TESTING ONLY: If history is empty, let's make some fake cards

        val rv = view.findViewById<RecyclerView>(R.id.rvTimeline)
        rv.layoutManager = LinearLayoutManager(requireContext())
        
        val surgeryDate = repository.getPatientProfile().surgeryDate
        rv.adapter = TimelineAdapter(history, surgeryDate)
    }
}