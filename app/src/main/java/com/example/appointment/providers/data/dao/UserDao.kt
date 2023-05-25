package com.example.appointment.providers.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appointment.models.entities.UserEntity


@Dao
interface UserDao  {
    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUser (email:String): List<UserEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)  //статегия если записть повторилась
    suspend fun  addUser (userEntity: UserEntity)

}