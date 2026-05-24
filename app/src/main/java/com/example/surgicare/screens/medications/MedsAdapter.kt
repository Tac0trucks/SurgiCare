package com.example.surgicare.screens.medications

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.surgicare.R

class MedsAdapter(
    private var medications: List<MedicationStatus>,
    private val onStreakClick: (String) -> Unit,
    private val onFinishCourseClick: (String) -> Unit,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<MedsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMedName: TextView = view.findViewById(R.id.tvMedName)
        val tvMedDesc: TextView = view.findViewById(R.id.tvMedDesc)
        val tvMedDosage: TextView = view.findViewById(R.id.tvMedDosage)
        val tvStreakCount: TextView = view.findViewById(R.id.tvStreakCount)
        val btnStreak: AppCompatButton = view.findViewById(R.id.btnStreak)
        val btnFinishCourse: View = view.findViewById(R.id.btnFinishCourse)
        val btnDeleteMed: View = view.findViewById(R.id.btnDeleteMed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medication_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val med = medications[position]
        holder.tvMedName.text = med.name
        holder.tvStreakCount.text = "${med.streak} 🔥"
        
        if (med.reminderTime != null) {
            holder.tvMedDesc.text = "Reminder: ${med.reminderTime}"
        } else {
            holder.tvMedDesc.text = "No reminder set"
        }

        if (med.dosage != null) {
            holder.tvMedDosage.text = "Dosage: ${med.dosage}"
            holder.tvMedDosage.visibility = View.VISIBLE
        } else {
            holder.tvMedDosage.visibility = View.GONE
        }

        if (med.isTakenToday) {
            holder.btnStreak.isEnabled = false
            holder.btnStreak.text = "Taken"
            holder.btnStreak.setBackgroundColor(Color.parseColor("#E2E8F0"))
        } else {
            holder.btnStreak.isEnabled = true
            holder.btnStreak.text = "+1 Streak"
            holder.btnStreak.setBackgroundColor(Color.parseColor("#009689"))
        }

        holder.btnStreak.setOnClickListener {
            onStreakClick(med.name)
        }
        
        holder.btnFinishCourse.setOnClickListener {
            onFinishCourseClick(med.name)
        }
        
        holder.btnDeleteMed.setOnClickListener {
            onDeleteClick(med.name)
        }
    }

    override fun getItemCount() = medications.size

    fun updateData(newMedications: List<MedicationStatus>) {
        medications = newMedications
        notifyDataSetChanged()
    }
}
