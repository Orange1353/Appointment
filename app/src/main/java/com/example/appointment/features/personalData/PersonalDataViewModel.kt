package com.example.appointment.features.eventPage


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appointment.core.utils.KEY_NAME
import com.example.appointment.features.personalData.PersonalData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PersonalDataViewModel @Inject constructor (
    //private val repository : BaseAuthRepository
) : ViewModel(){
    private lateinit var database: DatabaseReference
    val personalDataFromDatabase = MutableLiveData<PersonalData>()

    val userUId = MutableLiveData<String>("")
    val name = MutableLiveData<String>("")
    val day = MutableLiveData<String>("")
    val team = MutableLiveData<String>("")
    val camo = MutableLiveData<String>("")


    fun getDatabaseReference(){
        database = Firebase.database.reference
    }
    fun writeUser(userUId: String, name: String, day: String ,team: String, camo: String) {
        val user = PersonalData(userUId, name, day, team, camo)
        database.child("users").child(userUId).setValue(user)
    }
    fun reedOnceUser(userId: String) {
        database.child("users").child(userId).get().addOnSuccessListener {
            userUId.value = (it.value as HashMap<String, String>)["userUId"].orEmpty()
            name.value = (it.value as HashMap<String, String>)[KEY_NAME]
            day.value = (it.value as HashMap<String, String>)["day"]
            team.value = (it.value as HashMap<String, String>)["team"]
            camo.value = (it.value as HashMap<String, String>)["camo"]
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }
     fun getNewPersonalData (): PersonalData{
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