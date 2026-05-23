package com.example.surgicare.screens.medications

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.surgicare.MainActivity
import com.example.surgicare.R
import com.example.surgicare.data.repository.PatientRepository
import java.util.Calendar

class MedicationReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val medName = intent.getStringExtra(NotificationHelper.EXTRA_MED_NAME) ?: return

        // Build Intent to open the app (specifically the Medications Tab ideally, but MainActivity is fine)
        val mainIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
            .setSmallIcon(R.drawable.pill) // Make sure this icon exists
            .setContentTitle("Medication Reminder")
            .setContentText("It's time to take your $medName!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        try {
            with(NotificationManagerCompat.from(context)) {
                // Use hashcode as notification ID so multiple meds can show notifications
                notify(medName.hashCode(), builder.build())
            }
        } catch (e: SecurityException) {
            // Missing POST_NOTIFICATIONS permission
        }

        // Reschedule for next day based on saved time
        val repo = PatientRepository(context)
        val timeStr = repo.getReminderTime(medName)
        if (timeStr != null) {
            val parts = timeStr.split(":")
            if (parts.size == 2) {
                val hour = parts[0].toIntOrNull()
                val minute = parts[1].toIntOrNull()
                if (hour != null && minute != null) {
                    NotificationHelper.scheduleMedicationReminder(context, medName, hour, minute)
                }
            }
        }
    }
}
