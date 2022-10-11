package com.maverick.trrev

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.maverick.trrev.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var name: String = ""
    private var email: String = ""
    private var gender: String = ""
    private var month: String = ""
    private var year: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.male.setOnClickListener {
            if (binding.male.cardBackgroundColor.defaultColor == getColor(R.color.white)) {
                gender = "M"
                binding.male.setCardBackgroundColor(getColor(R.color.orange))
                binding.maleText.setTextColor(getColor(R.color.white))
            } else {
                gender = ""
                binding.male.setCardBackgroundColor(getColor(R.color.white))
                binding.maleText.setTextColor(getColor(R.color.orange))

            }
        }
        binding.female.setOnClickListener {
            if (binding.female.cardBackgroundColor.defaultColor == getColor(R.color.white)) {
                gender = "F"
                binding.female.setCardBackgroundColor(getColor(R.color.orange))
                binding.femaleText.setTextColor(getColor(R.color.white))
            } else {
                gender = ""
                binding.female.setCardBackgroundColor(getColor(R.color.white))
                binding.femaleText.setTextColor(getColor(R.color.orange))
            }
        }
        binding.other.setOnClickListener {
            if (binding.other.cardBackgroundColor.defaultColor == getColor(R.color.white)) {
                gender = "O"
                binding.other.setCardBackgroundColor(getColor(R.color.orange))
                binding.otherText.setTextColor(getColor(R.color.white))
            } else {
                gender = ""
                binding.other.setCardBackgroundColor(getColor(R.color.white))
                binding.otherText.setTextColor(getColor(R.color.orange))
            }
        }

        binding.submit.setOnClickListener {
            name = binding.fullName.text.toString().trim()
            Log.e("name", name)
            email = binding.emailId.text.toString()
            month = binding.month.text.toString()
            year = binding.years.text.toString()
            if (name.isEmpty() || email.isEmpty() || month.isEmpty() || year.isEmpty() || gender.isEmpty()) {
                Toast.makeText(applicationContext, "please fill all the fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
            call()

        }
    }

    private fun call() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://199.192.26.248:8000/sap/opu/odata/sap/ZCDS_TEST_REGISTER_CDS/ZCDS_TEST_REGISTER/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("email", email)
        jsonObject.put("gender", gender)
        jsonObject.put("practice_frm_month", month)
        jsonObject.put("practice_frm_year", year)

        val jsonObjectString = jsonObject.toString()
        val requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObjectString)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.createUser(requestBody)
            Log.e("printer", response.code().toString())

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { Log.e("print", it.string()) }
                } else {
                    Log.e("print_error", "error")

                }
            }
        }
    }
}