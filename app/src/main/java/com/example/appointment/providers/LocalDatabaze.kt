package com.example.appointment.providers

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appointment.data.dao.UserDao
import com.example.appointment.models.entities.UserEntity

@Database(
    version = 1,
    entities = [UserEntity::class]
)

abstract class LocalDatabaze : RoomDatabase() {

    abstract fun getUserDao ():UserDao

}