package com.example.surgicare.screens.assessment


import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.surgicare.R
import com.google.android.material.button.MaterialButton

class UploadPhotoFragment : Fragment(R.layout.fragment_upload_photo) {

    private var selectedUri: Uri? = null

    // Registering the gallery picker
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedUri = it
            // Visually indicate success
            view?.findViewById<MaterialButton>(R.id.btnContinue)?.isEnabled = true
            view?.findViewById<MaterialButton>(R.id.btnContinue)?.setBackgroundColor(
                android.graphics.Color.parseColor("#009689")
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.uploadArea).setOnClickListener {
            getContent.launch("image/*")
        }

        view.findViewById<MaterialButton>(R.id.btnContinue).setOnClickListener {
            // Move to Step 2
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, SymptomCheckFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<View>(R.id.btnCancel).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}