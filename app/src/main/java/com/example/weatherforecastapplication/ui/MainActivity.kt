package com.example.weatherforecastapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.databinding.ActivityMainBinding
import com.example.weatherforecastapplication.LanguageUtils
import com.example.weatherforecastapplication.data.db.PreferenceManager
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private lateinit var navController: NavController
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var binding:ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    @Inject lateinit var preferenceManager: PreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        LanguageUtils.setDefaultLanguage(preferenceManager,applicationContext)
        setContentView(binding.root)
        initNavHostFragment()
        initDrawerLayout()
        initDrawerLayoutMenu()
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("TAG", "onRestart:main ", )
    }

    override fun onResume() {
        super.onResume()
        LanguageUtils.setDefaultLanguage(preferenceManager,this)

    }

    override fun onStart() {
        super.onStart()
        LanguageUtils.setDefaultLanguage(preferenceManager,this)

    }


    private fun initNavHostFragment() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }

    private fun initDrawerLayoutMenu() {
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.settingFragment -> {
                    navController.navigate(R.id.settingFragment)
                    drawerLayout.closeDrawers()
                    true
                }

                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
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
