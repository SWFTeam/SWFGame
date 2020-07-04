package com.example.swfgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class Challenges : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenges)

        getSupportActionBar()?.setTitle("SWFGame - Challenges")

        this.bottomNav = findViewById(R.id.activity_main_bottom_navigation)
        bottomNav.selectedItemId = R.id.action_challenges

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
                    true
                }
                R.id.action_events -> {
                    println("Clicked on events")
                    val intent = Intent(this, Events::class.java)
                    startActivity(intent)
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