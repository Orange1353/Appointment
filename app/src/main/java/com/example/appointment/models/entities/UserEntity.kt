package com.example.appointment.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.appointment.models.local.UserModel

@Entity(
    tableName = "user",
    indices =[
        Index("email", unique = true)
    ]
)
data class UserEntity (
    @ColumnInfo(name ="email", collate = ColumnInfo.NOCASE)
    val email :String,
    @PrimaryKey
    val username  : String,
        ) {

    companion object{
        fun convertEntityToModel(userEntity: UserEntity): UserModel{
            return UserModel(username = userEntity.username, email = userEntity.email )
        }
        fun convertModelToEntity(userModel: UserModel): UserEntity{
            return UserEntity(username = userModel.username, email = userModel.email )
        }
    }
}