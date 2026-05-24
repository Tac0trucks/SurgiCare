package com.example.surgicare.screens.assessment


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.surgicare.R
import com.example.surgicare.data.repository.PatientRepository
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UploadPhotoFragment : Fragment(R.layout.fragment_upload_photo) {

    private var selectedUri: Uri? = null

    // Registering the gallery picker
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedUri = it
            // Visually indicate success
            val btnContinue = view?.findViewById<MaterialButton>(R.id.btnContinue)
            btnContinue?.isEnabled = true
            btnContinue?.setBackgroundColor(
                android.graphics.Color.parseColor("#009689")
            )
            
            // Show preview
            view?.findViewById<android.widget.ImageView>(R.id.ivPreview)?.apply {
                setImageURI(it)
                visibility = View.VISIBLE
            }
            
            // Update prompt texts
            view?.findViewById<android.widget.TextView>(R.id.tvUploadTitle)?.apply {
                text = "Retake/Change Photo"
                setTextColor(android.graphics.Color.WHITE)
            }
            view?.findViewById<android.widget.TextView>(R.id.tvUploadSubtitle)?.visibility = View.GONE
            
            // Add semi-transparent background to the text container so it's readable over the image
            view?.findViewById<android.widget.LinearLayout>(R.id.uploadPrompt)?.apply {
                backgroundTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#80000000"))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val repository = PatientRepository(requireContext())
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val lastUpload = repository.getLastPhotoUploadDate()
        
        val btnContinue = view.findViewById<MaterialButton>(R.id.btnContinue)
        val uploadArea = view.findViewById<View>(R.id.uploadArea)
        val tvUploadTitle = view.findViewById<TextView>(R.id.tvUploadTitle)
        val tvUploadSubtitle = view.findViewById<TextView>(R.id.tvUploadSubtitle)

        if (today == lastUpload) {
            btnContinue.isEnabled = false
            uploadArea.isClickable = false
            uploadArea.alpha = 0.5f
            tvUploadTitle.text = "Locked"
            tvUploadSubtitle.text = "Next photo allowed tomorrow."
        } else {
            btnContinue.isEnabled = false
            uploadArea.setOnClickListener {
                getContent.launch("image/*")
            }
        }

        btnContinue.setOnClickListener {
            if (selectedUri == null) return@setOnClickListener
            // Move to Step 2
            val nextFragment = SymptomCheckFragment().apply {
                arguments = Bundle().apply {
                    putString("photoUri", selectedUri.toString())
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, nextFragment)
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<View>(R.id.btnCancel).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}