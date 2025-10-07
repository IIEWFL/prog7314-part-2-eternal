package com.example.myapplication.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.model.Property
import com.example.myapplication.network.PropertyApi
import com.example.myapplication.network.RetrofitClient
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPropertyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        // ✅ Toolbar with back arrow
        val toolbar: MaterialToolbar = findViewById(R.id.addPropertyToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // ✅ UI Elements
        val etTitle: EditText = findViewById(R.id.etTitle)
        val etPrice: EditText = findViewById(R.id.etPrice)
        val etBedrooms: EditText = findViewById(R.id.etBedrooms)
        val etBathrooms: EditText = findViewById(R.id.etBathrooms)
        val etType: EditText = findViewById(R.id.etType)
        val etLocation: EditText = findViewById(R.id.etLocation)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        // ✅ Button click
        btnSubmit.setOnClickListener {
            val title = etTitle.text.toString()
            val price = etPrice.text.toString().toIntOrNull()
            val bedrooms = etBedrooms.text.toString().toIntOrNull()
            val bathrooms = etBathrooms.text.toString().toIntOrNull()
            val type = etType.text.toString()
            val location = etLocation.text.toString()

            if (title.isEmpty() || price == null || type.isEmpty()) {
                Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val property = Property(
                id = 0,
                title = title,
                price = price,
                bedrooms = bedrooms ?: 0,
                bathrooms = bathrooms ?: 0,
                type = type,
                location = location
            )

            // ✅ Call API to add property
            val api = RetrofitClient.instance.create(PropertyApi::class.java)
            api.addProperty(property).enqueue(object : Callback<Property> {
                override fun onResponse(call: Call<Property>, response: Response<Property>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddPropertyActivity, "Property added successfully!", Toast.LENGTH_LONG).show()
                        finish() // go back to previous screen
                    } else {
                        Toast.makeText(this@AddPropertyActivity, "Failed to add property", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Property>, t: Throwable) {
                    Toast.makeText(this@AddPropertyActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

/**

 * Android Developers, 2024. *Building Forms with Input Controls*. [online]
 * Available at: <https://developer.android.com/guide/topics/ui/controls>

 * Android Developers, 2024. *Handling User Input in Android*. [online]
 * Available at: <https://developer.android.com/training/keyboard-input/validate-input>

 * Google Developers, 2024. *Toolbar and App Bar Design Guidelines*. [online]
 * Available at: <https://developer.android.com/develop/ui/views/appbar>

 * Google Developers, 2024. *Intents and Navigation Between Activities*. [online]
 * Available at: <https://developer.android.com/guide/components/intents-filters>

 * Square, 2024. *Retrofit: Type-Safe HTTP Client for Android and Java*. [online]
 * Available at: <https://square.github.io/retrofit/>

 * Square, 2024. *Retrofit Call and Callback Interface Documentation*. [online]
 * Available at: <https://square.github.io/retrofit/#calls>

 */