package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.PropertyAdapter
import com.example.myapplication.R
import com.example.myapplication.model.Property
import com.example.myapplication.network.PropertyApi
import com.example.myapplication.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var tvPriceLabel: TextView
    private lateinit var seekBarPrice: SeekBar
    private lateinit var recyclerProperties: RecyclerView
    private lateinit var adapter: PropertyAdapter
    private val properties = mutableListOf<Property>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // ✅ Initialize Views
        tvPriceLabel = findViewById(R.id.tvPriceLabel)
        seekBarPrice = findViewById(R.id.seekBarPrice)
        recyclerProperties = findViewById(R.id.recyclerProperties)
        val spinnerBedrooms: Spinner = findViewById(R.id.spinnerBedrooms)
        val spinnerBathrooms: Spinner = findViewById(R.id.spinnerBathrooms)
        val spinnerType: Spinner = findViewById(R.id.spinnerType)
        val btnSearch: Button = findViewById(R.id.btnSearch)
        val btnAddProperty: Button = findViewById(R.id.btnAddProperty)
        val btnApplyFilters: Button = findViewById(R.id.btnApplyFilters)

        // ✅ Recycler setup
        recyclerProperties.layoutManager = LinearLayoutManager(this)
        adapter = PropertyAdapter(properties)
        recyclerProperties.adapter = adapter

        // ✅ Update label as seekbar moves
        seekBarPrice.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvPriceLabel.text = "Max Price: R$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // ✅ Search button clicked
        btnSearch.setOnClickListener {
            val maxPrice = seekBarPrice.progress
            val bedrooms = spinnerBedrooms.selectedItem.toString().toIntOrNull()
            val bathrooms = spinnerBathrooms.selectedItem.toString().toIntOrNull()
            val type =
                if (spinnerType.selectedItem.toString() == "Any") null else spinnerType.selectedItem.toString()

            searchProperties(0, maxPrice, bedrooms, bathrooms, type)
        }

        // ✅ Apply Filters (works the same as Search)
        btnApplyFilters.setOnClickListener {
            val minPrice = 0
            val maxPrice = seekBarPrice.progress
            val bedrooms = spinnerBedrooms.selectedItem.toString().toIntOrNull()
            val bathrooms = spinnerBathrooms.selectedItem.toString().toIntOrNull()
            val type =
                if (spinnerType.selectedItem.toString() == "Any") null else spinnerType.selectedItem.toString()

            searchProperties(minPrice, maxPrice, bedrooms, bathrooms, type)
        }

        // ✅ Add Property — Navigate to AddPropertyActivity
        btnAddProperty.setOnClickListener {
            val intent = Intent(this, AddPropertyActivity::class.java)
            startActivity(intent)
        }
    }

        // ✅ Function to call REST API
    private fun searchProperties(
        minPrice: Int,
        maxPrice: Int,
        bedrooms: Int?,
        bathrooms: Int?,
        type: String?
    ) {
        val api = RetrofitClient.instance.create(PropertyApi::class.java)
        api.getProperties(minPrice, maxPrice, bedrooms, bathrooms, type)
            .enqueue(object : Callback<List<Property>> {
                override fun onResponse(
                    call: Call<List<Property>>,
                    response: Response<List<Property>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        properties.clear()
                        properties.addAll(response.body()!!)
                        adapter.notifyDataSetChanged()

                        if (properties.isEmpty()) {
                            Toast.makeText(this@SearchActivity, "No matching properties found", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@SearchActivity, "Found ${properties.size} properties", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@SearchActivity, "Error: No data received", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Property>>, t: Throwable) {
                    Toast.makeText(this@SearchActivity, "API Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
    }
}


/**
 * Android Developers, 2024. *Displaying Lists with RecyclerView*. [online]
 * Available at: <https://developer.android.com/develop/ui/views/layout/recyclerview>

 *
 * Android Developers, 2024. *SeekBar and User Input Widgets*. [online]
 * Available at: <https://developer.android.com/reference/android/widget/SeekBar>

 *
 * Android Developers, 2024. *Intents and Activity Navigation*. [online]
 * Available at: <https://developer.android.com/guide/components/intents-filters>

 *
 * Google Developers, 2024. *Using Spinners and Input Controls in Android*. [online]
 * Available at: <https://developer.android.com/guide/topics/ui/controls/spinner>

 *
 * Google Developers, 2024. *Working with Event Listeners in Android*. [online]
 * Available at: <https://developer.android.com/guide/topics/ui/ui-events>

 *
 * Square, 2024. *Retrofit: Type-Safe HTTP Client for Android and Java*. [online]
 * Available at: <https://square.github.io/retrofit/>

 */