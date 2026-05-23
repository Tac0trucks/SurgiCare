package com.example.surgicare.screens.medications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.surgicare.R

class MedsAdapter(
    private var medications: List<MedicationStatus>,
    private val onStreakClick: (String) -> Unit
) : RecyclerView.Adapter<MedsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMedName: TextView = view.findViewById(R.id.tvMedName)
        val tvStreakCount: TextView = view.findViewById(R.id.tvStreakCount)
        val btnStreak: AppCompatButton = view.findViewById(R.id.btnStreak)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medication_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val med = medications[position]
        holder.tvMedName.text = med.name
        holder.tvStreakCount.text = "${med.streak} 🔥"

        if (med.isTakenToday) {
            holder.btnStreak.isEnabled = false
            holder.btnStreak.text = "Taken"
            holder.btnStreak.alpha = 0.5f
        } else {
            holder.btnStreak.isEnabled = true
            holder.btnStreak.text = "+1 Streak"
            holder.btnStreak.alpha = 1.0f
        }

        holder.btnStreak.setOnClickListener {
            onStreakClick(med.name)
        }
    }

    override fun getItemCount() = medications.size

    fun updateData(newMedications: List<MedicationStatus>) {
        medications = newMedications
        notifyDataSetChanged()
    }
}
