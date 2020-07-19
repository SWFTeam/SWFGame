package com.example.swfgame

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.EXTRA_MESSAGES
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_android_2.MyRecyclerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class Advices : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<*>
    lateinit var manager: RecyclerView.LayoutManager
    lateinit var token: String
    var items: Array<Advice> = emptyArray()
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advices)

        getSupportActionBar()?.setTitle("SWFGame - Advices")

        this.token = intent.getStringExtra("token")
        this.email = intent.getStringExtra("email")

        getAllAdvices()

        this.bottomNav = findViewById(R.id.activity_main_bottom_navigation)
        bottomNav.selectedItemId = R.id.action_advices

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("token", this.token)
                    intent.putExtra("email", this.email)
                    this.finish()
                    startActivity(intent)
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
                    true
                }
                else -> false
            }
        }
    }

    private fun getAllAdvices(){

        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        retIn.getAllAdvices(this.token).enqueue(object : Callback<Array<Advice>> {
            override fun onFailure(call: Call<Array<Advice>?>, t: Throwable) {
                Toast.makeText(
                    this@Advices,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
                println("ERROR : " + t.message)
            }

            private fun onClickItem(view: View, advice: Advice){

                var intent = Intent(applicationContext, AdviceDetails::class.java)
                intent.putExtra("id", advice.getDescriptions()?.get(0)?.getId())
                intent.putExtra("country_code", advice.getDescriptions()?.get(0)?.getCountryCode())
                intent.putExtra("title", advice.getDescriptions()?.get(0)?.getTitle())
                intent.putExtra("name", advice.getDescriptions()?.get(0)?.getName())
                intent.putExtra("description", advice.getDescriptions()?.get(0)?.getDescription())
                intent.putExtra("type", advice.getDescriptions()?.get(0)?.getType())
                intent.putExtra("foreign_id", advice.getDescriptions()?.get(0)?.getForeignId())
                intent.putExtra("token", token)
                intent.putExtra("email", email)
                startActivity(intent)
            }

            override fun onResponse(call: Call<Array<Advice>>, response: Response<Array<Advice>>) {
                if (response.code() == 200) {
                    items = response.body()!!
                    recyclerView = findViewById(R.id.list_recycler_view)

                    adapter = MyRecyclerAdapter(items, this::onClickItem)
                    manager = LinearLayoutManager(this@Advices)

                    recyclerView.apply {
                        this.layoutManager = manager
                        this.adapter = adapter
                    }

                    recyclerView.adapter = adapter

                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@Advices, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}