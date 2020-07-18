package com.example.swfgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getSupportActionBar()?.setTitle("SWFGame - Login")

        var emailTextView = findViewById<EditText>(R.id.email_text)
        var passwordTextView = findViewById<EditText>(R.id.password_text)
        var registerTextView = findViewById<TextView>(R.id.register_textview)
        registerTextView.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        var loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            println("LOGIN")

            signin(emailTextView.text.toString(), passwordTextView.text.toString())

        }
    }

    private fun signin(email: String, password: String){
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val signInInfo = SignInBody(email, password)
        retIn.signin(signInInfo).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    Toast.makeText(this@LoginActivity, "Login success! ", Toast.LENGTH_SHORT).show()
                    var tmp = response.body()?.string()
                    var responseSplit = tmp?.split('"')
                    var token = responseSplit?.get(3)
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    //println("ICI    " + response.body()?.string())
                    intent.putExtra("token", token)
                    intent.putExtra("email", email)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}

interface ApiInterface {
    @Headers("Content-Type:application/json")
    @POST("signin")
    fun signin(@Body info: SignInBody): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("signup")
    fun signup(@Body info: TEST): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @GET("bo/advices")
    fun getAllAdvices(@Header("Authorization") token: String): retrofit2.Call<Array<Advice>>

    @Headers("Content-Type:application/json")
    @GET("bo/challenges")
    fun getAllChallenges(@Header("Authorization") token: String): retrofit2.Call<ArrayList<Challenge>>

    @Headers("Content-Type:application/json")
    @GET("bo/events")
    fun getAllEvents(@Header("Authorization") token: String): retrofit2.Call<Array<Event>>

    @Headers("Content-Type:application/json")
    @POST("bo/user")
    fun getUserByMail(@Header("Authorization") token: String, @Body info: GetUserBody): retrofit2.Call<User>

    @Headers("Content-Type:application/json")
    @POST("completed")
    fun getCompletedChallenges(@Header("Authorization") token: String, @Body info: GetCompletedBody): retrofit2.Call<CompletedResult>

    @Headers("Content-Type:application/json")
    @POST("achieve")
    fun completeChallenge(@Header("Authorization") token: String, @Body info: AchieveBody): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("challenge")
    fun getChallenge(@Header("Authorization") token: String, @Body info: SendId): retrofit2.Call<ChallengeResult>

    @Headers("Content-Type:application/json")
    @POST("participated")
    fun getParticipatedEvents(@Header("Authorization") token: String, @Body info: GetCompletedBody): retrofit2.Call<CompletedResult>

    @Headers("Content-Type:application/json")
    @POST("participate")
    fun participateEvent(@Header("Authorization") token: String, @Body info: ParticipateBody): retrofit2.Call<ResponseBody>

}
class RetrofitInstance {
    companion object {
        val BASE_URL: String = "http://192.168.1.75:3000/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
        fun getRetrofitInstance(): Retrofit {
            val test = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return test
        }
    }
}


data class UserBody(val token: String)

data class SignInBody(val email: String, val password: String)

data class SignUpBody(val firstname: String, val lastname: String, val email_address: String, val password: String, val birthday: String, val address: Address, val address_work: Address)

data class Needs(val hasCar: Boolean, val hasBike: Boolean)

data class TEST(val user: SignUpBody, val needs: Needs)

data class GetUserBody(val email: String)

data class GetCompletedBody(val userEmail: String)

data class CompletedResult(val completed: Array<Int>)

data class AchieveBody(val userEmail: String, val challId: Int)

data class SendId(val id: Int)

data class ChallengeResult(val challenge: Challenge)

data class ParticipateBody(val userEmail: String, val eventId: Int)