package com.example.appointment.hilt

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [IAppSettings] based on [SharedPreferences].
 */
@Singleton
class SharedPreferencesAppSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : IAppSettings {

    // settings это название файла в котором храняться ключ/значения
    private val sharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun setCurrentToken(token: String?) {
        val editor = sharedPreferences.edit()
        if (token == null)
            //remove - удалить нафиг всю запись по ключу если значение null
            editor.remove(PREF_CURRENT_ACCOUNT_TOKEN)
        else
            editor.putString(PREF_CURRENT_ACCOUNT_TOKEN, token)
        editor.apply()
    }

    override fun getCurrentToken(): String? =
        sharedPreferences.getString(PREF_CURRENT_ACCOUNT_TOKEN, null)   //nul дефолтное значение которое вернется если переменная по ключу не найдена

    companion object {
        //PREF_CURRENT_ACCOUNT_TOKEN это хадкоженый ключ
        private const val PREF_CURRENT_ACCOUNT_TOKEN = "currentToken"
    }

}
/** Примепр использования SharedPreferences */