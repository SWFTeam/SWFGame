package com.example.swfgame

import ApiInterface
import SignInBody
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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