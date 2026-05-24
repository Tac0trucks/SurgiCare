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

class ProgressFragment : Fragment() {

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return try {
            inflater.inflate(R.layout.fragment_progress, container, false)
        } catch (t: Throwable) {
            val tv = android.widget.TextView(requireContext())
            tv.text = "CRASH IN FRAGMENT XML: ${t.stackTraceToString()}"
            tv
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val repository = PatientRepository(requireContext())
            val history = repository.getHealingHistory().reversed()

            val rv = view.findViewById<RecyclerView>(R.id.rvTimeline)
            rv.layoutManager = LinearLayoutManager(requireContext())
            
            val surgeryDate = repository.getPatientProfile().surgeryDate
            rv.adapter = TimelineAdapter(history, surgeryDate)
        } catch (e: Exception) {
            android.widget.Toast.makeText(requireContext(), "Error loading progress: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
        }
    }
}