package com.example.myapplication.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Property

class SavedPropertiesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SavedPropertyAdapter
    private val savedList = mutableListOf<Property>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_properties)

        recyclerView = findViewById(R.id.recyclerSaved)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SavedPropertyAdapter(savedList)
        recyclerView.adapter = adapter

        loadSavedProperties()
    }

    private fun loadSavedProperties() {
        val sharedPrefs = getSharedPreferences("SavedProperties", Context.MODE_PRIVATE)
        val savedSet = sharedPrefs.getStringSet("saved", emptySet())

        if (savedSet.isNullOrEmpty()) {
            Toast.makeText(this, "No saved properties found.", Toast.LENGTH_SHORT).show()
            return
        }

        savedList.clear()
        for (entry in savedSet) {
            val parts = entry.split("|")
            if (parts.size >= 6) {
                val property = Property(
                    id = parts[0].toInt(),
                    title = parts[1],
                    price = parts[2].toInt(),
                    bedrooms = parts[3].toInt(),
                    bathrooms = parts[4].toInt(),
                    type = parts[5],
                    location = if (parts.size > 6) parts[6] else "Unknown"
                )
                savedList.add(property)
            }
        }
        adapter.notifyDataSetChanged()
    }
}

/**

 * Android Developers, 2024. *Data Storage: SharedPreferences Overview*. [online]
 * Available at: <https://developer.android.com/training/data-storage/shared-preferences>

 *
 * Android Developers, 2024. *Displaying Lists with RecyclerView*. [online]
 * Available at: <https://developer.android.com/develop/ui/views/layout/recyclerview>

 *
 * Android Studio Documentation, 2024. *Managing Data Collections in Kotlin*. [online]
 * Available at: <https://developer.android.com/kotlin/collections>

 *
 * Google Developers, 2024. *Best Practices for Using Adapters in RecyclerView*. [online]
 * Available at: <https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter>

 * Google, 2024. *Local Data Persistence in Android Applications*. [online]
 * Available at: <https://developers.google.com/android/guides/data-storage>

 */
