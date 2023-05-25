package com.example.appointment.providers

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appointment.models.entities.UserEntity
import com.example.appointment.providers.data.dao.UserDao

@Database(
    version = 1,
    entities = [UserEntity::class]
)
// описание обьекта Room
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getUserDao (): UserDao

}