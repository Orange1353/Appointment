package com.example.appointment.features.auth

import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointment.data.repositories.BaseAuthRepository
import com.example.appointment.data.repositories.IUserRepisitory
import com.example.appointment.hilt.IAppSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository : BaseAuthRepository
) : ViewModel() {
    @Inject
    lateinit var settings: IAppSettings
    @Inject
    lateinit var userRepository: IUserRepisitory

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val currentUser get() = _firebaseUser

    //create our channels that will be used to pass messages to the main ui
    //create event channel
    private val eventsChannel = Channel<AllEvents>()
    //the messages passed to the channel shall be received as a Flowable
    //in the ui
    val allEventsFlow = eventsChannel.receiveAsFlow()

    //validate all fields first before performing any sign in operations
    fun signInUser(email: String , password: String) = viewModelScope.launch{
        when {
            email.isEmpty() -> {
                eventsChannel.send(AllEvents.ErrorCode(1))
            }
            password.isEmpty() -> {
                eventsChannel.send(AllEvents.ErrorCode(2))
            }
            else -> {
                actualSignInUser(email , password)
            }
        }
    }

    fun isAuthUser(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    private fun actualSignInUser(email:String, password: String) = viewModelScope.launch {
        try {
            val user = repository.signInWithEmailPassword(email, password)
            user?.let {
                eventsChannel.send(AllEvents.LogInSuccess("login success"))
            }
        } catch(e:Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(ControlsProviderService.TAG, "signInUser: ${error[1]}")
            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }
}