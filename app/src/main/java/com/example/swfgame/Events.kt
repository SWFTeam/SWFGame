package com.example.swfgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class Events : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        getSupportActionBar()?.setTitle("SWFGame - Events")

        this.bottomNav = findViewById(R.id.activity_main_bottom_navigation)
        bottomNav.selectedItemId = R.id.action_events

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_home -> {
                    println("Clicked on home")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_challenges -> {
                    println("Clicked on challenges")
                    val intent = Intent(this, Challenges::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_events -> {
                    println("Clicked on events")
                    true
                }
                R.id.action_advices -> {
                    println("Clicked on advices")
                    val intent = Intent(this, Advices::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}