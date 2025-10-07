package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ui.ListPropertyActivity
import com.example.myapplication.ui.MapActivity
import com.example.myapplication.ui.SearchActivity
import com.example.myapplication.ui.SavedPropertiesActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        // ✅ Setup Toolbar
        toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "Home icon clicked", Toast.LENGTH_SHORT).show()
        }

        // ✅ Quick Action Cards
        val cardSearch: MaterialCardView = findViewById(R.id.cardSearch)
        val cardList: MaterialCardView = findViewById(R.id.cardList)
        val cardMap: MaterialCardView = findViewById(R.id.cardMap)
        val cardSaved: MaterialCardView = findViewById(R.id.cardSaved)

        // Open SearchActivity
        cardSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        // Open ListPropertyActivity
        cardList.setOnClickListener {
            startActivity(Intent(this, ListPropertyActivity::class.java))
        }

        // Open MapActivity
        cardMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        // ✅ Open SavedPropertiesActivity (your new addition)
        cardSaved.setOnClickListener {
            startActivity(Intent(this, SavedPropertiesActivity::class.java))
        }

        // ✅ Button for listing properties (bottom button)
        val btnListProperties: Button = findViewById(R.id.btnListProperties)
        btnListProperties.setOnClickListener {
            startActivity(Intent(this, ListPropertyActivity::class.java))
        }
    }

    // Inflate menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
        return true
    }

    // Handle menu clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.action_logout -> {
                auth.signOut()
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

/**

 * Android Developers, 2024. *Activities and Intents | Android Developers*. [online]
 * Available at: <https://developer.android.com/guide/components/activities/intro-activities>

 * Android Studio Documentation, 2024. *Using Material Components in Android Studio*. [online]
 * Available at: <https://developer.android.com/guide/topics/ui/look-and-feel/material-components>

 *
 * Firebase, 2024. *Authenticate Using Firebase Authentication on Android*. [online]
 * Available at: <https://firebase.google.com/docs/auth/android/start>


 * Google Developers, 2024. *AppCompatActivity and Toolbar Overview*. [online]
 * Available at: <https://developer.android.com/reference/androidx/appcompat/app/AppCompatActivity>

 * Material Design, 2024. *Material Components for Android: Cards and Buttons*. [online]
 * Available at: <https://m3.material.io/components/cards/overview>

 */