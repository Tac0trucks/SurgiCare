package com.example.surgicare.screens.progress

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
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
        val tvDay: TextView? = view.findViewById(R.id.tvDayNumber)
        val tvDate: TextView? = view.findViewById(R.id.tvDate)
        val tvStatus: TextView? = view.findViewById(R.id.tvStatus)
        val llStatusBadge: View? = view.findViewById(R.id.llStatusBadge)
        val dot: View? = view.findViewById(R.id.viewStatusDot)
        val ivPhoto: android.widget.ImageView? = view.findViewById(R.id.ivWoundPhoto)
        val llSymptoms: View? = view.findViewById(R.id.llSymptoms)
        val tvSymptoms: TextView? = view.findViewById(R.id.tvSymptoms)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return try {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timeline_card, parent, false)
            ViewHolder(view)
        } catch (t: Throwable) {
            val errorView = android.widget.TextView(parent.context)
            errorView.text = "CRASH INFLATING: ${t.stackTraceToString()}"
            ViewHolder(errorView)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val record = history[position]
            // Calculate chronological day (since history is reversed)
            val chronologicalIndex = history.size - 1 - position
            val dayNumber = chronologicalIndex + 1

            holder.tvDay?.text = "Day $dayNumber"
            
            // Format Status Badge
            val baseColor = Color.parseColor(record.colorHex)
            holder.tvStatus?.text = record.status.name.lowercase().replaceFirstChar { it.titlecase() }
            holder.tvStatus?.setTextColor(baseColor)
            
            // Set dot color to match status
            val dotBackground = GradientDrawable()
            dotBackground.shape = GradientDrawable.OVAL
            dotBackground.setColor(baseColor)
            holder.dot?.background = dotBackground

            // Set badge background (20% opacity of base color)
            val badgeBackground = GradientDrawable()
            badgeBackground.shape = GradientDrawable.RECTANGLE
            badgeBackground.cornerRadius = 32f
            badgeBackground.setColor(Color.argb(51, Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor)))
            holder.llStatusBadge?.background = badgeBackground

            // Bind Symptoms
            if (!record.symptomsList.isNullOrEmpty()) {
                holder.llSymptoms?.visibility = View.VISIBLE
                holder.tvSymptoms?.text = record.symptomsList.joinToString(", ")
            } else {
                holder.llSymptoms?.visibility = View.GONE
            }
            
            // Bind Photo
            if (!record.photoUri.isNullOrEmpty()) {
                try {
                    holder.ivPhoto?.visibility = View.VISIBLE
                    holder.ivPhoto?.setImageURI(android.net.Uri.parse(record.photoUri))
                } catch (t: Throwable) {
                    holder.ivPhoto?.setImageResource(R.drawable.ic_image_placeholder)
                    holder.ivPhoto?.scaleType = android.widget.ImageView.ScaleType.CENTER_INSIDE
                }
            } else {
                holder.ivPhoto?.visibility = View.VISIBLE
                holder.ivPhoto?.setImageResource(R.drawable.ic_image_placeholder) // Use placeholder icon
                holder.ivPhoto?.scaleType = android.widget.ImageView.ScaleType.CENTER_INSIDE // Fix scale for icon
            }

            // Bind Date
            try {
                val parser = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val date = parser.parse(surgeryDateStr)
                if (date != null) {
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    calendar.add(Calendar.DAY_OF_YEAR, chronologicalIndex)
                    val formatter = SimpleDateFormat("MMM dd", Locale.getDefault())
                    holder.tvDate?.text = formatter.format(calendar.time)
                }
            } catch (e: Throwable) {
                holder.tvDate?.text = "Day $dayNumber"
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    override fun getItemCount() = history.size
}