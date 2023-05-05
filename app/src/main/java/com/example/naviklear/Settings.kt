package com.example.naviklear

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        val f: Fragment = SettingsFragment()
        ft.add(R.id.settingsFragmentContainer, f)
        ft.commit()




        /*var textSizeOption = findViewById(R.xml.preferences.)
        val context: Context = this@FilterRatingActivity
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sp.edit()
        editor.putLong("minimumRating", rating)
        editor.apply()*/
    }
}