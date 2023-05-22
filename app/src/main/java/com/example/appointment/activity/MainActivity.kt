package com.example.appointment.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appointment.R
import com.example.appointment.core.extentions.orIfNull
import com.example.appointment.models.local.UserModel
import com.example.appointment.providers.Repositories
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Repositories.init(applicationContext)

        //for test room
        lifecycleScope.launch {
            Repositories.userRepisitory.addUser(UserModel("someName", "someEmail"))
            val someUser = Repositories.userRepisitory.getUser("someEmail")
            someUser?.let { user ->
                Toast.makeText(this@MainActivity.applicationContext, "$user", Toast.LENGTH_SHORT)
                    .show()
            }.orIfNull {
                Toast.makeText(
                    this@MainActivity.applicationContext,
                    "not find",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}
