package com.example.swfgame

import ApiInterface
import CompletedResult
import GetCompletedBody
import SendId
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_android_2.MyRecyclerAdapter3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFrament2 : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<*>
    lateinit var manager: RecyclerView.LayoutManager
    lateinit var token: String
    lateinit var email: String
    var index = 0

    var items: ArrayList<Event>? = null

    companion object {
        fun newInstance(token: String, email: String): MyFrament2 {

            val f = MyFrament2()

            val bdl = Bundle(2)

            bdl.putString("token", token)
            bdl.putString("email", email)

            f.setArguments(bdl)

            return f

        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = inflater.inflate(R.layout.fragment_events, container, false);

        this.token = arguments!!.getString("token")
        this.email = arguments!!.getString("email")

        getParticipatedEvents()

        return view
    }

    private fun getEvent(eventId: Int) {

        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val requestBody = SendId(eventId)
        retIn.getEvent(this.token, eventId).enqueue(object :
            Callback<Event> {
            override fun onFailure(call: Call<Event>, t: Throwable) {

                println("ERROR : " + t.message)
            }

            private fun onClickItem(view: View, event: Event){

                var intent = Intent(context, EventDetails::class.java)
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

            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.code() == 200) {

                    if(items.isNullOrEmpty()){
                        items = arrayListOf(response.body()!!)
                    } else {
                        items!!.add(response.body()!!)
                    }

                    recyclerView = view!!.findViewById(R.id.completed_events_recyclerView) as RecyclerView

                    adapter = MyRecyclerAdapter3(items!!, this::onClickItem)
                    manager = LinearLayoutManager(this@MyFrament2.context)

                    recyclerView.apply {
                        this.layoutManager = manager
                        this.adapter = adapter
                    }

                    recyclerView.adapter = adapter

                    adapter.notifyDataSetChanged()

                } else {
                    println("Failed!")
                }
            }
        })
    }

    private fun getParticipatedEvents(){

        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val UserBody = GetCompletedBody(this.email)
        retIn.getParticipatedEvents(this.token, UserBody).enqueue(object :
            Callback<CompletedResult> {
            override fun onFailure(call: Call<CompletedResult>, t: Throwable) {

                println("ERROR : " + t.message)
            }

            override fun onResponse(call: Call<CompletedResult>, response: Response<CompletedResult>) {
                if (response.code() == 200) {
                    var completedIds = response.body()?.completed
                    if (completedIds != null) {
                        completedIds.forEach {

                            val eventId = it.toInt()
                            getEvent(eventId)
                        }
                    }
                } else {
                    println("Failed!")
                }
            }
        })
    }


}