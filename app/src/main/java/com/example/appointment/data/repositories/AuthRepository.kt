package com.example.appointment.data.repositories

import com.example.appointment.providers.firebase.BaseAuthenticator
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authenticator : BaseAuthenticator
) : BaseAuthRepository {
    override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
        return authenticator.signInWithEmailPassword(email , password)
    }

    override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
        return authenticator.signUpWithEmailPassword(email , password)
    }

    override fun signOut(): FirebaseUser? {
        return authenticator.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return authenticator.getUser()
    }

    override suspend fun sendResetPassword(email: String): Boolean {
        authenticator.sendPasswordReset(email)
        return true
    }
}
interface BaseAuthRepository {

    //this is an interface that will implement all common authentication
    //functions. That is sign in  , sign up , logout. Taking this approach will allow us
    // to rely on abstractions rather than a concrete authentication repository class. This
    //will make it easy for us to test and maintain in that whatever form of repository class
    //we will use it won't matter since all will inherit from this class. So swapping
    //of repositories will be easy.

    suspend fun signInWithEmailPassword(email:String , password:String): FirebaseUser?

    suspend fun signUpWithEmailPassword(email: String , password: String): FirebaseUser?

    fun signOut() : FirebaseUser?

    fun getCurrentUser() : FirebaseUser?

    suspend fun sendResetPassword(email : String) : Boolean
}