package com.example.swfgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_challenge_details.*

class ChallengeDetails : AppCompatActivity() {

    lateinit var title_textView: TextView
    lateinit var description_textView: TextView
    lateinit var bottomNav: BottomNavigationView
    lateinit var complete_button: Button
    lateinit var token: String
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_details)

        getSupportActionBar()?.setTitle("SWFGame - Challenge details")

        this.token = intent.getStringExtra("token")
        this.email = intent.getStringExtra("email")

        this.bottomNav = findViewById(R.id.activity_main_bottom_navigation)
        bottomNav.selectedItemId = R.id.action_challenges

        this.complete_button = findViewById(R.id.complete_button)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_home -> {
                    println("Clicked on home")
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("token", this.token)
                    intent.putExtra("email", this.email)
                    startActivity(intent)
                    true
                }
                R.id.action_challenges -> {
                    println("Clicked on challenges")
                    this.finish()
                    true
                }
                R.id.action_events -> {
                    println("Clicked on events")
                    val intent = Intent(this, Events::class.java)
                    intent.putExtra("token", this.token)
                    intent.putExtra("email", this.email)
                    startActivity(intent)
                    true
                }
                R.id.action_advices -> {
                    println("Clicked on advices")
                    val intent = Intent(this, Advices::class.java)
                    intent.putExtra("token", this.token)
                    intent.putExtra("email", this.email)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        this.complete_button.setOnClickListener {
            //Code to complete challenge
            this.finish()
        }

        var id = intent.getStringExtra("id")
        var country_code = intent.getStringExtra("country_code")
        var title = intent.getStringExtra("title")
        var name = intent.getStringExtra("name")
        var description = intent.getStringExtra("description")
        var type = intent.getStringExtra("type")
        var foreign_id = intent.getStringExtra("foreign_id")

        this.title_textView = findViewById(R.id.titleChallenge_textView)
        this.title_textView.text = title

        this.description_textView = findViewById(R.id.descriptionChallenge_textView)
        this.description_textView.text = "Description : " + description
    }
}