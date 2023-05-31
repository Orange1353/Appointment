package com.example.appointment.providers.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appointment.data.dao.UserDao
import com.example.appointment.models.entities.UserEntity

@Database(
    version = 1,
    entities = [UserEntity::class]
)
// описание обьекта Room
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getUserDao (): UserDao

}