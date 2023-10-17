package com.example.appointment.models.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameEventModel(
    val day: String? = null,
    val logo: String? = null,
    val gameEventName: String? = null,
    val orgName: String? = null,
    val idGame: String? = null
): Parcelable