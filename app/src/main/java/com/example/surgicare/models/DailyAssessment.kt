package com.example.surgicare.models

import android.net.Uri

data class DailyAssessment(
    var photoUri: Uri? = null,
    var painScore: Int = 1,
    val symptoms: MutableMap<String, Boolean> = mutableMapOf()
)
