package com.example.appointment.features

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment: Fragment() {

    //видимость нижнего меню
    var bottomNavListener: OnBottomNavigationVisibilityListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBottomNavigationVisibilityListener) {
            bottomNavListener = context
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("CURRENT FRAGMENT IS", "$this")
    }

}