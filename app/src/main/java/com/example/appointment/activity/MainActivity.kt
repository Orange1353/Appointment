package com.example.appointment.activity

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.appointment.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.bottomBar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupBottomMenu()
       // Repositories.init(applicationContext)

        //for test room
 /*       lifecycleScope.launch {
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

        }*/
    }

    fun setupBottomMenu() {
        val navController = this.findNavController(R.id.jhg)
        // магия из-за реализации меню в библиотеке smoothbottombar
        val popupMenu = PopupMenu(this, null)
        popupMenu.menuInflater.inflate(R.menu.menu_bottom, popupMenu.menu)

        bottomBar.setupWithNavController(popupMenu.menu, navController)
    }

}
