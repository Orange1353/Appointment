package com.example.appointment.di

import android.content.Context
import androidx.room.Room
import com.example.appointment.hilt.IAppSettings
import com.example.appointment.hilt.SharedPreferencesAppSettings
import com.example.appointment.providers.LocalDatabase
import com.example.appointment.providers.data.dao.UserDao
import com.example.appointment.providers.data.repositories.IUserRepisitory
import com.example.appointment.providers.data.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModel {
    @Binds
    abstract fun bindAppSettings(appSettings: SharedPreferencesAppSettings): IAppSettings
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepository: UserRepository
    ): IUserRepisitory
}
@Module
@InstallIn(SingletonComponent::class)
internal class SettingsBilderModel{
    @Provides
    fun provideUserDao(localDatabase: LocalDatabase): UserDao {
        return localDatabase.getUserDao()
    }
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): LocalDatabase {
        return  Room.databaseBuilder(appContext, LocalDatabase::class.java, "database.db")
            .build()
    }
}