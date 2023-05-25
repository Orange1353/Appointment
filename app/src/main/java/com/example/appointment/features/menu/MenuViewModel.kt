package com.example.appointment.features.menu

import androidx.lifecycle.ViewModel
import com.example.appointment.hilt.IAppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(): ViewModel() {
    @Inject
    lateinit var settings: IAppSettings

}