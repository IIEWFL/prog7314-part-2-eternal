package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        auth = FirebaseAuth.getInstance()

        val spinnerLanguage = findViewById<Spinner>(R.id.spinnerLanguage)
        val checkEmail = findViewById<CheckBox>(R.id.checkEmail)
        val checkSms = findViewById<CheckBox>(R.id.checkSms)
        val checkData = findViewById<CheckBox>(R.id.checkData)
        val checkLocation = findViewById<CheckBox>(R.id.checkLocation)
        val checkAccount = findViewById<CheckBox>(R.id.checkAccount)
        val switchDarkMode = findViewById<Switch>(R.id.switchDarkMode)
        val btnHelp = findViewById<Button>(R.id.btnHelp)
        val btnSignOut = findViewById<Button>(R.id.btnSignOut)

        // Populate language spinner
        val languages = resources.getStringArray(R.array.language_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        spinnerLanguage.adapter = adapter

        // Restore saved prefs
        spinnerLanguage.setSelection(prefs.getInt("language_index", 0))
        checkEmail.isChecked = prefs.getBoolean("notif_email", false)
        checkSms.isChecked = prefs.getBoolean("notif_sms", false)
        checkData.isChecked = prefs.getBoolean("privacy_data", false)
        checkLocation.isChecked = prefs.getBoolean("privacy_location", false)
        checkAccount.isChecked = prefs.getBoolean("privacy_account", false)
        switchDarkMode.isChecked = prefs.getBoolean("dark_mode", false)

        // Save prefs on change
        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, pos: Int, id: Long) {
                prefs.edit().putInt("language_index", pos).apply()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        checkEmail.setOnCheckedChangeListener { _, isChecked -> prefs.edit().putBoolean("notif_email", isChecked).apply() }
        checkSms.setOnCheckedChangeListener { _, isChecked -> prefs.edit().putBoolean("notif_sms", isChecked).apply() }
        checkData.setOnCheckedChangeListener { _, isChecked -> prefs.edit().putBoolean("privacy_data", isChecked).apply() }
        checkLocation.setOnCheckedChangeListener { _, isChecked -> prefs.edit().putBoolean("privacy_location", isChecked).apply() }
        checkAccount.setOnCheckedChangeListener { _, isChecked -> prefs.edit().putBoolean("privacy_account", isChecked).apply() }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Help button
        btnHelp.setOnClickListener {
            Toast.makeText(this, "Help and Support coming soon!", Toast.LENGTH_SHORT).show()
        }

        // Sign out
        btnSignOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}

/**

 * Android Developers, 2024. *Data Storage with SharedPreferences*. [online]
 * Available at: <https://developer.android.com/training/data-storage/shared-preferences>
 .
 *
 * Android Developers, 2024. *AppCompatDelegate and Dark Theme in Android*. [online]
 * Available at: <https://developer.android.com/develop/ui/views/theming/darktheme>

 *
 * Android Studio Documentation, 2024. *Creating and Managing UI Components in Android Studio*. [online]
 * Available at: <https://developer.android.com/guide/topics/ui>

 *
 * Firebase, 2024. *Manage Users with Firebase Authentication*. [online]
 * Available at: <https://firebase.google.com/docs/auth/android/manage-users>

 *
 * Google Developers, 2024. *Handling User Preferences and Settings in Android Apps*. [online]
 * Available at: <https://developers.google.com/android/guides/settings>

 *
 * Google, 2024. *Local Data Persistence Best Practices*. [online]
 * Available at: <https://developers.google.com/android/guides/data-storage>

 */