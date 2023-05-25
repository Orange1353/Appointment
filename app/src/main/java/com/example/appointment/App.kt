package com.example.appointment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application()  // запускает генерацию кода hilt для внедрения зависимотей