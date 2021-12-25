package com.example.quapp.menu_dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.quapp.R
import com.ismaeldivita.chipnavigation.ChipNavigationBar

open class UserMenu: AppCompatActivity() {

    private lateinit var chipNavBar: ChipNavigationBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_menu)
        initUI()

        chipNavBar.setItemSelected(R.id.bottom_nav_home, true)

        bottomMenu()
    }

    private fun bottomMenu() {
        var fragment: Fragment? = null
        chipNavBar.setOnItemSelectedListener {
            fragment = when (it) {
                R.id.bottom_nav_history -> UserHistoryFragment()
                R.id.bottom_nav_stats -> UserStatsFragment()
                R.id.bottom_nav_home -> UserHomeFragment()
                else -> null
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_nav, fragment!!).commit()
        }
    }

    private fun initUI() {
        chipNavBar = findViewById(R.id.bottom_nav_menu)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_nav, UserHomeFragment()).commit()
    }
}