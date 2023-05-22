package com.example.appointment.providers


import android.content.Context
import androidx.room.Room
import com.example.appointment.data.repositories.UserRepository

object Repositories {

    private lateinit var applicationContext: Context

    private val database: LocalDatabaze by lazy<LocalDatabaze> {
        Room.databaseBuilder(applicationContext, LocalDatabaze::class.java, "database.db")
            .build()
    }

    val userRepisitory: UserRepository by lazy {
        UserRepository(database.getUserDao()) // --- repositories
    }

    fun init(context: Context) {
        applicationContext = context
    }
}