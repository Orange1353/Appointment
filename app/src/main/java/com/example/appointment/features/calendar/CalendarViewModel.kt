package com.example.appointment.features.calendar

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appointment.data.repositories.IUserRepisitory
import com.example.appointment.hilt.IAppSettings
import com.example.appointment.models.local.GameEventModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CalendarViewModel @Inject constructor(
    //private val repository : BaseAuthRepository
) : ViewModel()
{
    @Inject
    lateinit var settings: IAppSettings
    @Inject
    lateinit var userRepository: IUserRepisitory

    val db = Firebase.firestore
    val docRef = db.collection(KEY_COLLECCTION)
    var eventList = MutableLiveData<List<GameEventModel>>()

    fun getDocumentOnFirebase(){
        val dockList = mutableListOf<GameEventModel>()
        docRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val logo = document[KEY_LOGO].toString()
                val nameGame = document[KEY_NAMEGAME].toString()
                val nameOrg = document[KEY_NAMEORG].toString()
                val timelinne = document[KEY_TIMELINNE].toString()
                dockList.add(GameEventModel(logo = logo, gameEventName = nameGame, orgName=nameOrg, timeLine=timelinne))
            }
            eventList.value = dockList
        }
    }
    //private val _firebaseUser = MutableLiveData<FirebaseUser?>()

    fun onListItemClick (position: Int){
        Log.e("position55555555","$position")
    }

    companion object {
        const val KEY_COLLECCTION= "gamers"
        const val KEY_LOGO = "Logo"
        const val KEY_NAMEGAME = "NameGame"
        const val KEY_NAMEORG = "NameOrg"
        const val KEY_TIMELINNE = "Time"
    }
}