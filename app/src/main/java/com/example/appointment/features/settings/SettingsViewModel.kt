package com.example.appointment.features.settings

import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointment.data.repositories.BaseAuthRepository
import com.example.appointment.data.repositories.IUserRepisitory
import com.example.appointment.features.auth.AllEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SettingsViewModel @Inject constructor(
   private val repository : BaseAuthRepository,
) : ViewModel() {

    @Inject
    lateinit var userRepository: IUserRepisitory

    private val eventsChannel = Channel<AllEvents>()
    val allEventsFlow = eventsChannel.receiveAsFlow()

    fun signOut() = viewModelScope.launch {
        try {
            val user = repository.signOut()
            user?.let {
                eventsChannel.send(AllEvents.Message("logout failure"))
            } ?: eventsChannel.send(AllEvents.LogOutSuccess("sign out successful"))
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(ControlsProviderService.TAG, "signInUser: ${error[1]}")
            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }
}