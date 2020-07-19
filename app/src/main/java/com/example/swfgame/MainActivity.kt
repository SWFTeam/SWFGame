package com.example.swfgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var viewpager: ViewPager
    private lateinit var tabs: TabLayout

    lateinit var token: String
    lateinit var bottomNav: BottomNavigationView
    lateinit var username_textView: TextView
    lateinit var level_textView: TextView
    lateinit var email: String
    lateinit var experience_progressBar: ProgressBar
    var user: User = User("ttt", "ttt", 1, "ttt", "ttt", 50)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.setTitle("SWFGame - Home")
        /*Commented to work on bottom menu*/
        if(intent.getStringExtra("token").isNullOrEmpty()){
            val intent = Intent(this, LoginActivity::class.java)
            this.finish()
            startActivity(intent)
        } else {
            this.token = intent.getStringExtra("token")
            this.email = intent.getStringExtra("email")

            this.username_textView = findViewById(R.id.username_textView)
            this.level_textView = findViewById(R.id.level_textView)
            this.experience_progressBar = findViewById(R.id.experience_progressBar)

            getUserByMail()

            //Setup TabViews
            initViews()
            setupViewPager()
        }

        this.bottomNav = findViewById(R.id.activity_main_bottom_navigation)
        bottomNav.selectedItemId = R.id.action_home
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_home -> {
                    true
                }
                R.id.action_challenges -> {
                    val intent = Intent(this, Challenges::class.java)
                    intent.putExtra("token", this.token)
                    intent.putExtra("email", this.email)
                    this.finish()
                    startActivity(intent)
                    true
                }
                R.id.action_events -> {
                    val intent = Intent(this, Events::class.java)
                    intent.putExtra("token", this.token)
                    intent.putExtra("email", this.email)
                    this.finish()
                    startActivity(intent)
                    true
                }
                R.id.action_advices -> {
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
    }

    private fun initViews() {
        tabs = findViewById(R.id.tabs)
        viewpager = findViewById(R.id.viewpager)
    }

    private fun setupViewPager() {

        val adapter = MyFragmentPagerAdapter(getSupportFragmentManager())

        var firstFragmet: MyFrament = MyFrament.newInstance(this.token, this.email)
        var secondFragmet: MyFrament2 = MyFrament2.newInstance(this.token, this.email)

        adapter.addFragment(firstFragmet, "Challenges √")
        adapter.addFragment(secondFragmet, "Events √")

        viewpager!!.adapter = adapter

        tabs!!.setupWithViewPager(viewpager)

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

                    if(user.getExperience() == 0 || user.getExperience() == null){

                        experience_progressBar.progress = 0
                        experience_progressBar.tooltipText = "Experience : " + 0 + "/10"

                        level_textView.text = "Level 1 - Exp "

                    } else {

                        var exp = user.getExperience()?.rem(10);

                        if (exp != null) {
                            experience_progressBar.progress = exp
                            experience_progressBar.tooltipText = "Experience : " + exp.toString() + "/10"
                        } else {
                            experience_progressBar.progress = 0
                        }

                        level_textView.text = "Level " + (user.getExperience()?.div(10)?.toInt()).toString() + " - Exp "
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}