package com.example.zooshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.zooshop.fragment.HomeFragment
import com.example.zooshop.fragment.InfoFragment
import com.example.zooshop.fragment.ProfileFragment
import com.example.zooshop.fragment.SearchFragment
import com.example.zooshop.storage.ZoobazarStorageProfile
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        switchFragmentOn(HomeFragment())
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.page_home -> {
                    switchFragmentOn(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.page_search -> {
                    switchFragmentOn(SearchFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.page_profile -> {
                    switchFragmentOn(ProfileFragment(ZoobazarStorageProfile(bottomNavigationView.context)))
                    return@setOnItemSelectedListener true
                }
                R.id.page_info -> {
                    switchFragmentOn(InfoFragment())
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
    }

    private fun switchFragmentOn(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}