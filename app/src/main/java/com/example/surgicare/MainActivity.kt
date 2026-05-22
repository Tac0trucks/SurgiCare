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

