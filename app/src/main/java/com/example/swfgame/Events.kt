package com.example.swfgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_android_2.MyRecyclerAdapter3
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Events : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<*>
    lateinit var manager: RecyclerView.LayoutManager
    lateinit var token: String
    var items: ArrayList<Event>? = null
    lateinit var bottomNav: BottomNavigationView
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        getSupportActionBar()?.setTitle("SWFGame - Events")

        this.token = intent.getStringExtra("token")
        this.email = intent.getStringExtra("email")

        getAllEvents()

        this.bottomNav = findViewById(R.id.activity_main_bottom_navigation)
        bottomNav.selectedItemId = R.id.action_events

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("token", token)
                    intent.putExtra("email", email)
                    this.finish()
                    startActivity(intent)
                    true
                }
                R.id.action_challenges -> {
                    val intent = Intent(this, Challenges::class.java)
                    intent.putExtra("token", token)
                    intent.putExtra("email", email)
                    this.finish()
                    startActivity(intent)
                    true
                }
                R.id.action_events -> {
                    true
                }
                R.id.action_advices -> {
                    val intent = Intent(this, Advices::class.java)
                    intent.putExtra("token", token)
                    intent.putExtra("email", email)
                    this.finish()
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun getAllEvents(){
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        retIn.getAllEvents(this.token).enqueue(object : Callback<ArrayList<Event>> {
            override fun onFailure(call: Call<ArrayList<Event>?>, t: Throwable) {
                Toast.makeText(
                    this@Events,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
                println("ERROR : " + t.message)
            }

            private fun onClickEvent(view: View, event: Event){

                var intent = Intent(applicationContext, EventDetails::class.java)
                intent.putExtra("id", event.getDescriptions()?.get(0)?.getId().toString())
                intent.putExtra("country_code", event.getDescriptions()?.get(0)?.getCountryCode().toString())
                intent.putExtra("title", event.getDescriptions()?.get(0)?.getTitle().toString())
                intent.putExtra("name", event.getDescriptions()?.get(0)?.getName().toString())
                intent.putExtra("description", event.getDescriptions()?.get(0)?.getDescription().toString())
                intent.putExtra("type", event.getDescriptions()?.get(0)?.getType().toString())
                intent.putExtra("foreign_id", event.getDescriptions()?.get(0)?.getForeignId().toString())
                intent.putExtra("country", event.getAddress()?.get(0)?.getCountry())
                intent.putExtra("city", event.getAddress()?.get(0)?.getCity().toString())
                intent.putExtra("street", event.getAddress()?.get(0)?.getStreet().toString())
                intent.putExtra("zip_code", event.getAddress()?.get(0)?.getZipCode().toString())
                intent.putExtra("nb_house", event.getAddress()?.get(0)?.getNbHouse().toString())
                intent.putExtra("complement", event.getAddress()?.get(0)?.getComplement().toString())
                intent.putExtra("date_start", event.getDateStart().toString())
                intent.putExtra("date_end", event.getDateEnd().toString())
                intent.putExtra("experience", event.getExperience().toString())
                intent.putExtra("token", token)
                intent.putExtra("email", email)
                intent.putExtra("event_id", event.getId().toString())
                startActivity(intent)
            }

            override fun onResponse(call: Call<ArrayList<Event>>, response: Response<ArrayList<Event>>) {
                if (response.code() == 200) {
                    items = response.body()!!
                    recyclerView = findViewById(R.id.list_recycler_view3)

                    adapter = MyRecyclerAdapter3(items!!, this::onClickEvent)
                    manager = LinearLayoutManager(this@Events)

                    recyclerView.apply {
                        this.layoutManager = manager
                        this.adapter = adapter
                    }

                    recyclerView.adapter = adapter

                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@Events, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}