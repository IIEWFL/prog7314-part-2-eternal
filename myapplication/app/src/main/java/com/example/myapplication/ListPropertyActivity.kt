package com.example.myapplication.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.PropertyAdapter
import com.example.myapplication.R
import com.example.myapplication.model.Property
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPropertyActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PropertyAdapter
    private val propertyList = mutableListOf<Property>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_property)

        recyclerView = findViewById(R.id.recyclerViewProperties)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PropertyAdapter(propertyList)
        recyclerView.adapter = adapter

        loadProperties()
    }

    private fun loadProperties() {
        val api = RetrofitClient.instance.create(ApiService::class.java)
        api.getProperties(0, Int.MAX_VALUE, null, null, null)
            .enqueue(object : Callback<List<Property>> {
                override fun onResponse(
                    call: Call<List<Property>>,
                    response: Response<List<Property>>
                ) {
                    if (response.isSuccessful) {
                        propertyList.clear()
                        response.body()?.let { propertyList.addAll(it) }
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(
                            this@ListPropertyActivity,
                            "Error loading properties",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<Property>>, t: Throwable) {
                    Toast.makeText(
                        this@ListPropertyActivity,
                        "API Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}

/**

 *
 * Android Developers, 2024. *RecyclerView Overview | Android Developers*. [online]
 * Available at: <https://developer.android.com/guide/topics/ui/layout/recyclerview>

 *
 * Google Developers, 2024. *AppCompatActivity and Activity Lifecycle*. [online]
 * Available at: <https://developer.android.com/reference/androidx/appcompat/app/AppCompatActivity>

 *
 * Square, 2024. *Retrofit: A Type-Safe HTTP Client for Android and Java*. [online]
 * Available at: <https://square.github.io/retrofit/>

 *
 * Android Developers, 2024. *Displaying Lists with RecyclerView*. [online]
 * Available at: <https://developer.android.com/develop/ui/views/layout/recyclerview>

 */