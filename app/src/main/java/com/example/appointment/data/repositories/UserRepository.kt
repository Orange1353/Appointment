package com.example.appointment.data.repositories

import com.example.appointment.data.dao.UserDao
import com.example.appointment.models.entities.UserEntity
import com.example.appointment.models.local.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userDao: UserDao,
) : iUserRepisitory {

    /**************************************/
    override suspend fun getUser(email: String): UserModel? = withContext(Dispatchers.IO) {
        val entity = userDao.getUser(email)?.firstOrNull()
        entity?.let {
            UserEntity.convertEntityToModel(it)
        }
    }

    override suspend fun addUser(user: UserModel) = withContext(Dispatchers.IO) {
        val entity = UserEntity.convertModelToEntity(user)
        userDao.addUser(entity)
    }

    /**************************************/
}
interface iUserRepisitory {
    suspend fun addUser (user: UserModel)
    suspend fun getUser (email: String):UserModel?

}