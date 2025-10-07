package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*

import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        // 🌐 Language
        findPreference<ListPreference>("pref_language")?.setOnPreferenceChangeListener { _, newValue ->
            Toast.makeText(requireContext(), "Language set to $newValue", Toast.LENGTH_SHORT).show()
            true
        }

        // 🌙 Dark Mode
        findPreference<SwitchPreferenceCompat>("pref_dark_mode")?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue as Boolean) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true
        }

        // 📞 Help
        findPreference<Preference>("pref_help")?.setOnPreferenceClickListener {
            Toast.makeText(requireContext(), "Redirecting to Help & Support", Toast.LENGTH_SHORT).show()
            true
        }

        // 🚪 Sign Out
        findPreference<Preference>("pref_sign_out")?.setOnPreferenceClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
            true
        }
    }
}

/**

 * Android Developers, 2024. *Settings with PreferenceFragmentCompat*. [online]
 * Available at: <https://developer.android.com/guide/topics/ui/settings>

 *
 * Android Developers, 2024. *Dark Theme with AppCompatDelegate Overview*. [online]
 * Available at: <https://developer.android.com/develop/ui/views/theming/darktheme>

 *
 * Firebase, 2024. *Manage User Sessions with Firebase Authentication*. [online]
 * Available at: <https://firebase.google.com/docs/auth/android/manage-users>

 *
 * Google Developers, 2024. *Creating Preference Screens in Android Apps*. [online]
 * Available at: <https://developers.google.com/android/guides/settings>

 *
 * Google Developers, 2024. *Handling User Input and Preference Changes*. [online]
 * Available at: <https://developer.android.com/guide/topics/ui/settings/use-preference>

 */
