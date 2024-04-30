package com.example.appointment.features.personalData

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointment.core.utils.CAMO_KEY
import com.example.appointment.core.utils.DAY_KEY
import com.example.appointment.core.utils.EMPTY_STRING
import com.example.appointment.core.utils.NAME_KEY
import com.example.appointment.core.utils.TEAM_KEY
import com.example.appointment.core.utils.USER_ID_KEY
import com.example.appointment.features.auth.AllEvents
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PersonalDataViewModel @Inject constructor () : ViewModel() {
    private lateinit var database: DatabaseReference

    private val eventsChannel = Channel<AllEvents>()
    val allEventsFlow = eventsChannel.receiveAsFlow()

    private val userUId = MutableStateFlow(EMPTY_STRING)
    val name = MutableStateFlow(EMPTY_STRING)
    val day = MutableStateFlow(EMPTY_STRING)
    val team = MutableStateFlow(EMPTY_STRING)
    val camo = MutableStateFlow(EMPTY_STRING)

    fun getDatabaseReference() {
        database = Firebase.database.reference
    }

    /** Save changes to server */
    fun writeUser() {
        val user = getNewPersonalData()
        database.child("users").child(user.userUId).setValue(user).addOnCompleteListener {
            viewModelScope.launch {
                if (it.isSuccessful) {
                    eventsChannel.send(AllEvents.Success)
                } else {
                    eventsChannel.send(AllEvents.Error("Попробуйте позже"))
                }
            }

        }
    }

    /** Get user data from server */
    fun reedUserOnce(userId: String) {
        database.child("users").child(userId).get().addOnSuccessListener {
            userUId.value = (it.value as? HashMap<String, String>)?.get(USER_ID_KEY).orEmpty()
            name.value = (it.value as? HashMap<String, String>)?.get(NAME_KEY).orEmpty()
            day.value = (it.value as? HashMap<String, String>)?.get(DAY_KEY).orEmpty()
            team.value = (it.value as? HashMap<String, String>)?.get(TEAM_KEY).orEmpty()
            camo.value = (it.value as? HashMap<String, String>)?.get(CAMO_KEY).orEmpty()
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }

    private fun getNewPersonalData(): PersonalData {
        return PersonalData(
            userUId = userUId.value.orEmpty(),
            name = name.value.orEmpty(),
            day = day.value.orEmpty(),
            team = team.value.orEmpty(),
            camo = camo.value.orEmpty()
        )
    }


//    val postListener = object : ValueEventListener{
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            val persData = dataSnapshot.getValue<PersonalData>()
//        }
//        override fun onCancelled(databaseError: DatabaseError) {
//            // Getting Post failed, log a message
//            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//        }
//    }

}