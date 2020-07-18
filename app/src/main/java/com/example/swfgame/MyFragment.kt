package com.example.swfgame

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_android_2.MyRecyclerAdapter2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.measureTimeMillis

class MyFrament : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<*>
    lateinit var manager: RecyclerView.LayoutManager
    lateinit var token: String
    lateinit var email: String
    var index = 0

    var items: ArrayList<Challenge>? = null

    companion object {
        fun newInstance(token: String, email: String): MyFrament {

            val f = MyFrament()

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
        var view: View? = inflater.inflate(R.layout.fragment_challenges, container, false);

        this.token = arguments!!.getString("token")
        this.email = arguments!!.getString("email")

        getCompletedChallenges()

        return view
    }

    private fun getChallenge(challId: Int) {

        println("Method called with id = " + challId)

        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val requestBody = SendId(challId)
        retIn.getChallenge(this.token, requestBody).enqueue(object :
            Callback<ChallengeResult> {
            override fun onFailure(call: Call<ChallengeResult>, t: Throwable) {

                println("ERROR : " + t.message)
            }

            private fun onClickItem(view: View, challenge: Challenge){

                println("Clicked on " + challenge.getDescription()?.get(0)?.getDescription())
                var intent = Intent(context, ChallengeDetails::class.java)
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

            override fun onResponse(call: Call<ChallengeResult>, response: Response<ChallengeResult>) {
                if (response.code() == 200) {

                    println("Challenge id : " + response.body()?.challenge?.getId())
                    println("Challenge experience : " + response.body()?.challenge?.getExperience())
                    println("Challenge title : " + response.body()?.challenge?.getDescription()?.get(0)?.getTitle())

                    if(items.isNullOrEmpty()){
                        items = arrayListOf(response.body()!!.challenge)
                    } else {
                        items!!.add(response.body()!!.challenge)
                    }

                    recyclerView = view!!.findViewById(R.id.completed_challenges_recyclerView) as RecyclerView

                    adapter = MyRecyclerAdapter2(items!!, this::onClickItem)
                    manager = LinearLayoutManager(this@MyFrament.context)

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

    private fun getCompletedChallenges(){

        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val UserBody = GetCompletedBody(this.email)
        retIn.getCompletedChallenges(this.token, UserBody).enqueue(object :
            Callback<CompletedResult> {
            override fun onFailure(call: Call<CompletedResult>, t: Throwable) {

                println("ERROR : " + t.message)
            }

            override fun onResponse(call: Call<CompletedResult>, response: Response<CompletedResult>) {
                if (response.code() == 200) {
                    var completedIds = response.body()?.completed
                    if (completedIds != null) {
                        completedIds.forEach {
                            println("Nouvel id : " + it.toString())

                            val challId = it.toInt()
                            getChallenge(challId)
                        }
                    }
                } else {
                    println("Failed!")
                }
            }
        })
    }


}