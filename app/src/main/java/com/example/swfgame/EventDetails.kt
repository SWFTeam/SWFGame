package com.example.swfgame

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventDetails : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
    lateinit var title_textView: TextView
    lateinit var location_textView: TextView
    lateinit var description_textView: TextView
    lateinit var full_address: String
    lateinit var letsgo_imageButton: ImageButton
    lateinit var letsgo_textView: TextView
    lateinit var dateStart_textView: TextView
    lateinit var dateEnd_textView: TextView
    lateinit var participate_button: Button
    lateinit var experience_textView: TextView
    lateinit var token: String
    lateinit var email: String
    lateinit var eventId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        getSupportActionBar()?.setTitle("SWFGame - Event details")

        this.token = intent.getStringExtra("token")
        this.email = intent.getStringExtra("email")
        this.eventId = intent.getStringExtra("event_id")

        getParticipatedEvents()

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
                    this.finish()
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

        var id = intent.getStringExtra("id")
        var title = intent.getStringExtra("title")
        var name = intent.getStringExtra("name")
        var description = intent.getStringExtra("description")
        var type = intent.getStringExtra("type")
        var foreign_id = intent.getStringExtra("foreign_id")
        var country_code = intent.getStringExtra("country_code")
        var city = intent.getStringExtra("city")
        var street = intent.getStringExtra("street")
        var country = intent.getStringExtra("country")
        var zip_code = intent.getStringExtra("zip_code")
        var nb_house = intent.getStringExtra("nb_house")
        var complement = intent.getStringExtra("complement")
        var date_start = intent.getStringExtra("date_start")
        var date_end = intent.getStringExtra("date_end")
        var experience = intent.getStringExtra("experience")

        this.full_address = nb_house.toString() + " " + street + ", " + zip_code + " " + city

        this.title_textView = findViewById(R.id.titleEvent_textView)
        this.title_textView.text = title

        this.location_textView = findViewById(R.id.location_textView)
        this.location_textView.text = "Localisation : " + this.full_address

        this.description_textView = findViewById(R.id.description_textView)
        this.description_textView.text = "Description : " + description

        this.letsgo_imageButton = findViewById(R.id.repere_imageButton)
        this.letsgo_imageButton.setOnClickListener {
            navigateMaps(nb_house, street, zip_code, city)
        }

        this.letsgo_textView = findViewById(R.id.letsgo_textView)
        this.letsgo_textView.setOnClickListener {
            navigateMaps(nb_house, street, zip_code, city)
        }

        this.dateStart_textView = findViewById(R.id.dateStart_textView)
        this.dateStart_textView.text = "Start date : " + date_start.subSequence(0, 10)

        this.dateEnd_textView = findViewById(R.id.dateEnd_textView)
        this.dateEnd_textView.text = "End date : " + date_end.subSequence(0, 10)

        this.participate_button = findViewById(R.id.participate_button)
        this.participate_button.setOnClickListener {
            //Participate action
            participateEvent()
            this.finish()
        }

        this.experience_textView = findViewById(R.id.experience_textView)
        this.experience_textView.text = "Cet event vous rapportera " + experience + " points d'experience."

    }

    private fun navigateMaps(nbHouse: String, street: String, zipCode: String, city: String){

        val address = nbHouse + "+" + street.replace(" ", "+") + ",+" + zipCode + "+" + city
        val uri = "https://www.google.fr/maps/place/" + address
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

    private fun getParticipatedEvents(){

        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val UserBody = GetCompletedBody(this.email)
        retIn.getParticipatedEvents(this.token, UserBody).enqueue(object :
            Callback<CompletedResult> {
            override fun onFailure(call: Call<CompletedResult>, t: Throwable) {
                Toast.makeText(
                    this@EventDetails,
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
                            if(it.toString() == eventId){
                                participate_button.setBackgroundColor(Color.BLACK)
                                participate_button.setOnClickListener {

                                }
                                participate_button.text = "Already participated"
                                //complete_button.setEnabled(false)
                            }
                        }
                    }
                } else {
                    Toast.makeText(this@EventDetails, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun participateEvent(){

        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val ParticipateBody = ParticipateBody(this.email, this.eventId.toInt())
        retIn.participateEvent(this.token, ParticipateBody).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@EventDetails,
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
                            Toast.makeText(this@EventDetails, "Event participated, your experience has been increased !", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@EventDetails, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}