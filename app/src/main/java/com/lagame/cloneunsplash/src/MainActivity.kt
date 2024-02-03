package com.lagame.cloneunsplash.src

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.lagame.cloneunsplash.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavControl()
    }

    private fun bottomNavControl(){
        binding.mainBottNav.itemIconTintList = null

        val navHostFragment = supportFragmentManager.findFragmentById(com.lagame.cloneunsplash.R.id.main_container) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.mainBottNav, navController)

    }

}