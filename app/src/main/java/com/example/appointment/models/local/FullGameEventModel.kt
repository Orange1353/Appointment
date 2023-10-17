package com.example.appointment.models.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FullGameEventModel(
    val about: String? = null
): Parcelable