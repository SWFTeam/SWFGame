package com.example.swfgame

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var firstnameEditText: EditText
    lateinit var lastnameEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var passwordConfirmEditText: EditText
    lateinit var birthDatePicker: DatePicker
    lateinit var countrySpinner: Spinner
    lateinit var cityEditText: EditText
    lateinit var streetNameEditText: EditText
    lateinit var zipcodeEditText: EditText
    lateinit var houseNumber: EditText
    lateinit var complementEditText: EditText
    lateinit var hasCarCehckBox: CheckBox
    lateinit var hasBikeCheckBox: CheckBox
    lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.firstnameEditText = findViewById<EditText>(R.id.firstname_editText)
        this.lastnameEditText = findViewById<EditText>(R.id.lastname_editText)
        this.emailEditText = findViewById<EditText>(R.id.email_editText)
        this.passwordEditText = findViewById<EditText>(R.id.password_editText)
        this.passwordConfirmEditText = findViewById<EditText>(R.id.confirm_editText)
        this.birthDatePicker = findViewById<DatePicker>(R.id.birth_datePicker)
        this.countrySpinner = findViewById<Spinner>(R.id.country_spinner)
        this.cityEditText = findViewById<EditText>(R.id.city_editText)
        this.streetNameEditText = findViewById<EditText>(R.id.street_editText)
        this.zipcodeEditText = findViewById<EditText>(R.id.zipcode_editText)
        this.houseNumber = findViewById<EditText>(R.id.housenumber_number)
        this.complementEditText = findViewById<EditText>(R.id.complement_editText)
        this.hasCarCehckBox = findViewById<CheckBox>(R.id.hascar_checkBox)
        this.hasBikeCheckBox = findViewById<CheckBox>(R.id.hasbike_checkBox)
        this.registerButton = findViewById<Button>(R.id.register_button)

        ArrayAdapter.createFromResource(
            this,
            R.array.country_arrays,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            countrySpinner.adapter = adapter
        }

        registerButton.setOnClickListener {
            if(!checkFill()){
                showFillDialog()
            } else {
                if(!checkPasswords()){
                    showPasswordDialog()
                } else {
                    val firstname = this.firstnameEditText.text.toString()
                    val lastname = this.lastnameEditText.text.toString()
                    val email = this.emailEditText.text.toString()
                    val password = this.passwordEditText.text.toString()
                    val birthday: String = checkDigit(this.birthDatePicker.dayOfMonth) + "/" + checkDigit(this.birthDatePicker.month + 1) + "/" + checkDigit(this.birthDatePicker.year)
                    val country = this.countrySpinner.selectedItem.toString()
                    val city = this.cityEditText.text.toString()
                    val streetName = this.streetNameEditText.text.toString()
                    val zipcode = this.zipcodeEditText.text.toString()
                    val houseNumber = this.houseNumber.text.toString()
                    val complement = this.complementEditText.text.toString()
                    val hasCar: Boolean = this.hasCarCehckBox.isChecked
                    val hasBike: Boolean = this.hasBikeCheckBox.isChecked

                    this.registerButton.setOnClickListener {
                        println("REGISTER")

                        signup(firstname, lastname, email, password, "12/06/1997", country, city, streetName, zipcode, houseNumber, complement, hasCar, hasBike)
                    }
                }
            }
        }

    }

    fun checkDigit(number: Int): String? {
        return if (number <= 9) "0$number" else number.toString()
    }

    fun checkFill(): Boolean {
        return !(this.firstnameEditText.text.isNullOrEmpty() || this.lastnameEditText.text.isNullOrEmpty() || emailEditText.text.isNullOrEmpty() || passwordEditText.text.isNullOrEmpty() || passwordConfirmEditText.text.isNullOrEmpty() || cityEditText.text.isNullOrEmpty() || streetNameEditText.text.isNullOrEmpty() || zipcodeEditText.text.isNullOrEmpty() || houseNumber.text.isNullOrEmpty())
    }

    fun checkPasswords(): Boolean {
        return this.passwordEditText.text.toString() == this.passwordConfirmEditText.text.toString()
    }

    fun showFillDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Erreur")
        builder.setMessage("Vous devez remplir tous les champs obligatoires.")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

        }
        builder.show()
    }

    fun showPasswordDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Erreur")
        builder.setMessage("Les mots de passes de correspondent pas ${this.passwordEditText.text} / ${this.passwordConfirmEditText.text}.")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

        }
        builder.show()
    }

    private fun signup(firstname: String, lastname: String, email: String, password: String, birthday: String, country: String, city: String, streetName: String, zipcode: String, houseNumber: String, complement: String, hasCar: Boolean, hasBike: Boolean){
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        println("HEREEEE    " + birthday)
        val signUpInfo = TEST(SignUpBody(firstname, lastname, email, password, birthday, Address(country, city, streetName, zipcode.toInt(), houseNumber.toInt(), complement), Address(country, city, streetName, zipcode.toInt(), houseNumber.toInt(), complement)), Needs(hasCar, hasBike))
        retIn.signup(signUpInfo).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200 || response.code() == 201) {
                    Toast.makeText(applicationContext, "Register success! ", Toast.LENGTH_SHORT).show()

                    var tmp = response.body()?.string()
                    var responseSplit = tmp?.split('"')
                    var token = responseSplit?.get(5)
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    intent.putExtra("token", token)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Register failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

