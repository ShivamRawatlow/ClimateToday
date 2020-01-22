package com.thelegendofawizard.climatetoday.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.thelegendofawizard.climatetoday.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )*/

        /*bottom_nav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController)*/


        setSupportActionBar(menu_toolbar)

        val navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        setupButtonNavMenu(navController)

        setupActionBar(navController)


    }




    private fun setupActionBar(navController: NavController)
    {
        NavigationUI.setupActionBarWithNavController(this,navController)
    }


    private fun setupButtonNavMenu(navController: NavController)
    {
        bottom_nav.let {
            NavigationUI.setupWithNavController(it,navController)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav,menu)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        val navigated = NavigationUI.onNavDestinationSelected(item,navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }


}
