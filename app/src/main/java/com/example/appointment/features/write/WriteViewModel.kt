package com.example.appointment.features.write

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appointment.data.repositories.BaseAuthRepository
import com.example.appointment.data.repositories.IUserRepisitory
import com.example.appointment.hilt.IAppSettings
import com.example.appointment.models.local.LocationModel
import com.example.appointment.models.local.RouteModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val repository : BaseAuthRepository
) : ViewModel() {
    @Inject
    lateinit var settings: IAppSettings
    @Inject
    lateinit var userRepository: IUserRepisitory

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val LoacationList: List<LocationModel> = listOf(LocationModel("Untolovo"), LocationModel("Solnechnoe") )
    val RouteList: List<RouteModel> = listOf(RouteModel("Family1"), RouteModel("Family2") , RouteModel("Family2"), RouteModel("Family2"))

    fun onListItemClick (position: Int){
        Log.e("position55555555","$position")
    }
}
