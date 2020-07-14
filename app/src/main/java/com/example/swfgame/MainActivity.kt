package com.example.swfgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var token: String
    lateinit var bottomNav: BottomNavigationView
    lateinit var username_textView: TextView
    lateinit var level_textView: TextView
    lateinit var email: String
    var user: User = User("ttt", "ttt", 1, "ttt", "ttt", 50)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.setTitle("SWFGame - Home")
        /*Commented to work on bottom menu*/
        if(intent.getStringExtra("token").isNullOrEmpty()){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            this.token = intent.getStringExtra("token")
            this.email = intent.getStringExtra("email")

            this.username_textView = findViewById(R.id.username_textView)
            this.level_textView = findViewById(R.id.level_textView)

            getUserByMail()
        }

        this.bottomNav = findViewById(R.id.activity_main_bottom_navigation)
        bottomNav.selectedItemId = R.id.action_home
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_home -> {
                    println("Clicked on home")
                    true
                }
                R.id.action_challenges -> {
                    println("Clicked on challenges")
                    val intent = Intent(this, Challenges::class.java)
                    intent.putExtra("token", this.token)
                    intent.putExtra("email", this.email)
                    startActivity(intent)
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
                    intent.putExtra("token", this.email)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun getUserByMail(){

        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val UserBody = GetUserBody(this.email)
        retIn.getUserByMail(this.token, UserBody).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
                println("ERROR : " + t.message)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == 200) {
                    user = response.body()!!
                    username_textView.text = user.getFirstname() + " " + user.getLastname()
                    level_textView.text = "Level " + (user.getExperience()?.div(10)?.toInt()).toString()
                    println("HERE " + user.getFirstname().toString())
                } else {
                    Toast.makeText(this@MainActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}