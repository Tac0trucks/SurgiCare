package com.example.surgicare

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.surgicare.screens.dashboard.DashboardFragment
import com.example.surgicare.screens.medications.MedsFragment
import com.example.surgicare.screens.progress.ProgressFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val crashPrefs = getSharedPreferences("crash_prefs", android.content.Context.MODE_PRIVATE)
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            crashPrefs.edit().putString("crash_log", exception.stackTraceToString()).commit()
            defaultHandler?.uncaughtException(thread, exception)
        }

        val crashLog = crashPrefs.getString("crash_log", null)
        if (crashLog != null) {
            crashPrefs.edit().remove("crash_log").commit()
            android.app.AlertDialog.Builder(this)
                .setTitle("FATAL CRASH CAUGHT")
                .setMessage(crashLog)
                .setPositiveButton("OK", null)
                .show()
        }
        val repository = com.example.surgicare.data.repository.PatientRepository(this)
        if (!repository.isProfileComplete()) {
            startActivity(android.content.Intent(this, com.example.surgicare.screens.landing.LandingActivity::class.java))
            finish()
            return
        }
        
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set default fragment
        loadFragment(DashboardFragment())

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(DashboardFragment())
                R.id.nav_progress -> loadFragment(ProgressFragment())
                R.id.nav_meds -> loadFragment(MedsFragment())
                R.id.nav_profile -> loadFragment(com.example.surgicare.screens.profile.ProfileFragment())
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
        return true
    }

    fun navigateToTab(itemId: Int) {
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.selectedItemId = itemId
    }
}

