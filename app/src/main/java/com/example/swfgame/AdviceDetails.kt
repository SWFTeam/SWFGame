package com.example.swfgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdviceDetails : AppCompatActivity() {

    lateinit var title_textView: TextView
    lateinit var description_textView: TextView
    lateinit var bottomNav: BottomNavigationView
    lateinit var gotIt_Button: Button
    lateinit var token: String
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice_details)

        getSupportActionBar()?.setTitle("SWFGame - Advice details")

        this.token = intent.getStringExtra("token")
        this.email = intent.getStringExtra("email")

        this.bottomNav = findViewById(R.id.activity_main_bottom_navigation)
        bottomNav.selectedItemId = R.id.action_advices

        this.gotIt_Button = findViewById(R.id.gotit_button)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_home -> {
                    println("Clicked on home")
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("token", this.token)
                    intent.putExtra("email", this.email)
                    this.finish()
                    startActivity(intent)
                    true
                }
                R.id.action_challenges -> {
                    println("Clicked on challenges")
                    val intent = Intent(this, Challenges::class.java)
                    intent.putExtra("token", this.token)
                    intent.putExtra("email", this.email)
                    this.finish()
                    startActivity(intent)
                    true
                }
                R.id.action_events -> {
                    println("Clicked on events")
                    val intent = Intent(this, Events::class.java)
                    intent.putExtra("token", this.token)
                    intent.putExtra("email", this.email)
                    this.finish()
                    startActivity(intent)
                    true
                }
                R.id.action_advices -> {
                    println("Clicked on advices")
                    this.finish()
                    true
                }
                else -> false
            }
        }

        this.gotIt_Button.setOnClickListener {
            this.finish()
        }

        var id = intent.getStringExtra("id")
        var country_code = intent.getStringExtra("country_code")
        var title = intent.getStringExtra("title")
        var name = intent.getStringExtra("name")
        var description = intent.getStringExtra("description")
        var type = intent.getStringExtra("type")
        var foreign_id = intent.getStringExtra("foreign_id")

        this.title_textView = findViewById(R.id.titleCompleted_textView)
        this.title_textView.text = title.toString()

        this.description_textView = findViewById(R.id.description_textView)
        this.description_textView.text = description.toString()

        println("ICIIII " + title)

    }
}