package com.example.surgicare.screens.progress

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surgicare.R
import com.example.surgicare.data.model.CheckupResult


class TimelineAdapter(private val history: List<CheckupResult>) :
    RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDay: TextView = view.findViewById(R.id.tvDayNumber)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val dot: View = view.findViewById(R.id.viewStatusDot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timeline_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = history[position]
        holder.tvDay.text = "Day ${position + 1}"
        holder.tvStatus.text = record.status.name
        holder.tvStatus.setTextColor(Color.parseColor(record.colorHex))
        // Set dot color to match status
        holder.dot.setBackgroundColor(Color.parseColor(record.colorHex))
    }

    override fun getItemCount() = history.size
}