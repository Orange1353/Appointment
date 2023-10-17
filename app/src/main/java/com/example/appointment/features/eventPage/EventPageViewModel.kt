package com.example.appointment.features.eventPage


import androidx.lifecycle.ViewModel
import com.example.appointment.data.repositories.IUserRepisitory
import com.example.appointment.hilt.IAppSettings
import com.example.appointment.models.local.FullGameEventModel
import com.example.appointment.models.local.GameEventModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EventPageViewModel @Inject constructor (
        //private val repository : BaseAuthRepository
) : ViewModel(){
    @Inject
    lateinit var settings: IAppSettings
    @Inject
    lateinit var userRepository: IUserRepisitory
    val db = Firebase.firestore
   // var selectedGameEvent:GameEventModel? = null


        fun getGameEventOnFirebase(selectedGameEvent:GameEventModel?) {
            val day = selectedGameEvent?.day.orEmpty()
            val nameOrg = selectedGameEvent?.orgName.orEmpty()
            val idGame = selectedGameEvent?.idGame.orEmpty()
            val docRef = db.collection("GameEventAbout").document(idGame)
            val dockList = mutableListOf<FullGameEventModel>()
            docRef.get().addOnSuccessListener { documents ->
                val about = documents[KEY_ABOUT].toString()
                dockList.add(FullGameEventModel(about = about))}

    }
    fun regaOnGame(){

    }
    companion object {
        const val KEY_ABOUT = "About"
    }
}