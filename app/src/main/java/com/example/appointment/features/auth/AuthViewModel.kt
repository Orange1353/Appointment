package com.example.appointment.features.auth

import androidx.lifecycle.ViewModel
import com.example.appointment.hilt.IAppSettings
import com.example.appointment.providers.data.repositories.IUserRepisitory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(

) : ViewModel() {
    @Inject
    lateinit var settings: IAppSettings
    @Inject
    lateinit var userRepository: IUserRepisitory
}