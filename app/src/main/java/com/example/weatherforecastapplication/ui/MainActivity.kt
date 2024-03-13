package com.example.weatherforecastapplication.ui

import android.content.Intent
import android.media.audiofx.Equalizer
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.WeatherApp
import com.example.weatherforecastapplication.databinding.ActivityMainBinding

import android.app.Activity
import android.content.ComponentName
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastapplication.ui.home.HomeViewModel


import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var binding:ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var viewModel: HomeViewModel // Replace with your actual ViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*if (!isNotificationServiceEnabled()) {
            // If not, open settings to enable it
            openNotificationAccessSettings()
        }*/
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        initDrawerLayout()
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settingFragment -> {
                    navController.navigate(R.id.settingFragment)
                    drawerLayout.closeDrawers()
                    true
                }

                R.id.favoriteFragment -> {
                    navController.navigate(R.id.favoriteFragment)
                    drawerLayout.closeDrawers()
                    true
                }

                R.id.weaherAlertsFragment -> {
                    navController.navigate(R.id.weaherAlertsFragment)
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }
    }


    private fun initDrawerLayout() {
        drawerLayout=binding.drawerLayout
        actionBarDrawerToggle= ActionBarDrawerToggle(this,drawerLayout,
            R.string.nav_open,R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun isNotificationServiceEnabled(): Boolean {
        val packageName = packageName
        val flat = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return flat != null && flat.contains(packageName)
    }

 /*   private fun openNotificationAccessSettings() {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        startActivity(intent)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_NOTIFICATION_ACCESS) {
            if (resultCode == Activity.RESULT_OK) {
                // Notification access granted, do something if needed
            } else {
                // Notification access denied, handle accordingly
            }
        }
    }*/


    companion object {
        const val REQUEST_CODE_NOTIFICATION_ACCESS = 123
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
