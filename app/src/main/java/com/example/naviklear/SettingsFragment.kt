package com.example.naviklear

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

    }
}