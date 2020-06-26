package com.example.swfgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent.getStringExtra("token").isNullOrEmpty()){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            var token = intent.getStringExtra("token")
            println("Token received : " + token)

            this.token = token

            println("Token received : " + this.token)
        }
    }
}