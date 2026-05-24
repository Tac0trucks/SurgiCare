package com.example.surgicare.screens.progress

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surgicare.R
import com.example.surgicare.data.model.CheckupResult

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TimelineAdapter(private val history: List<CheckupResult>, private val surgeryDateStr: String) :
    RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDay: TextView = view.findViewById(R.id.tvDayNumber)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val dot: View = view.findViewById(R.id.viewStatusDot)
        val ivPhoto: android.widget.ImageView = view.findViewById(R.id.ivWoundPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timeline_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val record = history[position]
            holder.tvDay.text = "Day ${position + 1}"
            holder.tvStatus.text = record.status.name
            holder.tvStatus.setTextColor(Color.parseColor(record.colorHex))
            // Set dot color to match status
            holder.dot.setBackgroundColor(Color.parseColor(record.colorHex))
            
            if (!record.photoUri.isNullOrEmpty()) {
                holder.ivPhoto.visibility = View.VISIBLE
                holder.ivPhoto.setImageURI(android.net.Uri.parse(record.photoUri))
            } else {
                holder.ivPhoto.visibility = View.VISIBLE
                holder.ivPhoto.setImageResource(R.drawable.ic_image_placeholder)
            }

            try {
                val parser = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val date = parser.parse(surgeryDateStr)
                if (date != null) {
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    calendar.add(Calendar.DAY_OF_YEAR, position)
                    val formatter = SimpleDateFormat("MMM dd", Locale.getDefault())
                    holder.tvDate.text = formatter.format(calendar.time)
                }
            } catch (e: Exception) {
                holder.tvDate.text = "Day ${position + 1}"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount() = history.size
}