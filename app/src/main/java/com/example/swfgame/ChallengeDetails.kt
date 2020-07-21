package com.example.swfgame

import AchieveBody
import ApiInterface
import CompletedResult
import GetCompletedBody
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.toColorInt
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChallengeDetails : AppCompatActivity() {

    lateinit var title_textView: TextView
    lateinit var description_textView: TextView
    lateinit var bottomNav: BottomNavigationView
    lateinit var complete_button: Button
    lateinit var token: String
    lateinit var email: String
    lateinit var experience_textView: TextView
    lateinit var challId: String
    lateinit var back_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_details)

        getSupportActionBar()?.setTitle("SWFGame - Challenge details")

        this.token = intent.getStringExtra("token")
        this.email = intent.getStringExtra("email")
        this.challId = intent.getStringExtra("id")

        getCompletedChallenges()

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
                    this.finish()
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
                    this.finish()
                    startActivity(intent)
                    true
                }
                R.id.action_advices -> {
                    println("Clicked on advices")
                    val intent = Intent(this, Advices::class.java)
                    intent.putExtra("token", this.token)
                    intent.putExtra("email", this.email)
                    this.finish()
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        this.complete_button.setOnClickListener {
            //Code to complete challenge
            completeChallenge()
            this.finish()
        }

        var id = intent.getStringExtra("id")
        var country_code = intent.getStringExtra("country_code")
        var title = intent.getStringExtra("title")
        var name = intent.getStringExtra("name")
        var description = intent.getStringExtra("description")
        var type = intent.getStringExtra("type")
        var foreign_id = intent.getStringExtra("foreign_id")
        var experience = intent.getStringExtra("experience")

        this.title_textView = findViewById(R.id.titleChallenge_textView)
        this.title_textView.text = title

        this.description_textView = findViewById(R.id.descriptionChallenge_textView)
        this.description_textView.text = "Description : " + description

        this.experience_textView = findViewById(R.id.experience_textView)
        this.experience_textView.text = "This challenge will make you gain " + experience + " experience points."
    }

    private fun getCompletedChallenges(){

        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val UserBody = GetCompletedBody(this.email)
        retIn.getCompletedChallenges(this.token, UserBody).enqueue(object : Callback<CompletedResult> {
            override fun onFailure(call: Call<CompletedResult>, t: Throwable) {
                Toast.makeText(
                    this@ChallengeDetails,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
                println("ERROR : " + t.message)
            }

            override fun onResponse(call: Call<CompletedResult>, response: Response<CompletedResult>) {
                if (response.code() == 200) {
                    var completedIds = response.body()?.completed
                    if (completedIds != null) {
                        completedIds.forEach {
                            if(it.toString() == challId){
                                complete_button.setBackgroundColor(Color.BLACK)
                                complete_button.setOnClickListener {

                                }
                                complete_button.text = "Already completed"
                                //complete_button.setEnabled(false)
                            }
                        }

                        back_button = findViewById(R.id.back_button)
                        back_button.setOnClickListener {
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(this@ChallengeDetails, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun completeChallenge(){

        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val UserBody = AchieveBody(this.email, this.challId.toInt())
        retIn.completeChallenge(this.token, UserBody).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@ChallengeDetails,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
                println("ERROR : " + t.message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    var response = response.body()?.string()
                    if (response != null) {
                        if(response.contains("successfully")){
                            Toast.makeText(this@ChallengeDetails, "Challenge achieved, your experience has been increased !", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@ChallengeDetails, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}