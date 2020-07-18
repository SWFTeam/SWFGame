package com.example.swfgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_android_2.MyRecyclerAdapter
import com.example.tp_android_2.MyRecyclerAdapter2
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Challenges : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<*>
    lateinit var manager: RecyclerView.LayoutManager
    lateinit var token: String
    var items: ArrayList<Challenge>? = null
    lateinit var bottomNav: BottomNavigationView
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenges)

        getSupportActionBar()?.setTitle("SWFGame - Challenges")

        this.token = intent.getStringExtra("token")
        this.email = intent.getStringExtra("email")

        getAllChallenges()

        this.bottomNav = findViewById(R.id.activity_main_bottom_navigation)
        bottomNav.selectedItemId = R.id.action_challenges

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
    }

    private fun getAllChallenges(){
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        retIn.getAllChallenges(this.token).enqueue(object : Callback<ArrayList<Challenge>> {
            override fun onFailure(call: Call<ArrayList<Challenge>?>, t: Throwable) {
                Toast.makeText(
                    this@Challenges,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
                println("ERROR : " + t.message)
            }

            private fun onClickItem(view: View, challenge: Challenge){

                println("Clicked on " + challenge.getDescription()?.get(0)?.getDescription())
                var intent = Intent(applicationContext, ChallengeDetails::class.java)
                intent.putExtra("id", challenge.getId().toString())
                intent.putExtra("country_code", challenge.getDescription()?.get(0)?.getCountryCode())
                intent.putExtra("title", challenge.getDescription()?.get(0)?.getTitle())
                intent.putExtra("name", challenge.getDescription()?.get(0)?.getName())
                intent.putExtra("description", challenge.getDescription()?.get(0)?.getDescription())
                intent.putExtra("type", challenge.getDescription()?.get(0)?.getType())
                intent.putExtra("foreign_id", challenge.getDescription()?.get(0)?.getForeignId())
                intent.putExtra("experience", challenge.getExperience().toString())
                intent.putExtra("token", token)
                intent.putExtra("email", email)
                startActivity(intent)
            }

            override fun onResponse(call: Call<ArrayList<Challenge>>, response: Response<ArrayList<Challenge>>) {
                if (response.code() == 200) {
                    items = response.body()!!
                    println("HERE " + items?.get(0)?.getDescription()?.get(0)?.getDescription())
                    recyclerView = findViewById(R.id.list_recycler_view2)

                    println("WESH   " + items!![0].getDescription()?.get(0)?.getDescription())

                    adapter = MyRecyclerAdapter2(items!!, this::onClickItem)
                    manager = LinearLayoutManager(this@Challenges)

                    recyclerView.apply {
                        this.layoutManager = manager
                        this.adapter = adapter
                    }

                    recyclerView.adapter = adapter

                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@Challenges, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}