
package com.example.surgicare.screens.landing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.surgicare.R
import com.example.surgicare.data.repository.PatientRepository
import com.example.surgicare.screens.registration.PatientRegisterActivity

class LandingActivity : AppCompatActivity(), LandingContract.View {
    private lateinit var presenter: LandingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        // Repository implementation omitted for brevity but should use PreferenceManager
        presenter = LandingPresenter(this, PatientRepository(this))

        // Find views from activity_landing_page.xml
        findViewById<View>(R.id.btnPatient).setOnClickListener {
            presenter.onRoleSelected(true)
        }

        findViewById<View>(R.id.btnDoctor).setOnClickListener {
            presenter.onRoleSelected(false)
        }
    }

    override fun startPatientFlow() {
        startActivity(Intent(this, PatientRegisterActivity::class.java))
    }

    override fun startDoctorFlow() {
        // Implementation for doctor flow
    }
}