package com.example.naviklear

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceFragmentCompat
import com.example.naviklear.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.checkerframework.checker.units.qual.Length
import java.util.*

/*
import com.google.android.gms.maps.MapRenderView
import com.google.android.gms.maps.MapController
*/

class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var MABottomNavigationBar : BottomNavigationView

    private lateinit var db : FirebaseFirestore

    private lateinit var goButton : Button
    private lateinit var feedbackButton : ImageButton
    private lateinit var favoriteButtton : ImageButton
    private lateinit var profileButton : ImageButton



    private lateinit var endingAddressEditText: EditText

    private lateinit var startTextViewMA : TextView
    private lateinit var endTextViewMA : TextView
    private lateinit var currentLocationTextView : TextView

    private lateinit var startingAddress : String
    private lateinit var endingAddress : String



    private lateinit var myMapView : MapView
    private lateinit var mMap : GoogleMap
    private lateinit var currentLocation : Location
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding.root)

        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sp.registerOnSharedPreferenceChangeListener(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getCurrentLocationUser()



        db = FirebaseFirestore.getInstance()


        var currentLocationTextView = findViewById<TextView>(R.id.currentLocationTextView)
        var endingAddressEditText = findViewById<EditText>(R.id.endingAddressEditText)

        var goButton = findViewById<Button>(R.id.goButton)


        var feedbackButton = findViewById<ImageButton>(R.id.feedbackImage)
        var profileButton = findViewById<ImageButton>(R.id.profileImage)
        var favoriteButton = findViewById<ImageButton>(R.id.favoriteImage)


        //Set user preferences on create
        startTextViewMA = findViewById(R.id.startTextViewMA)
        endTextViewMA = findViewById(R.id.EndTextViewMA)
        goButton = findViewById(R.id.goButton)
        endingAddressEditText = findViewById(R.id.endingAddressEditText)
        currentLocationTextView = findViewById(R.id.currentLocationTextView)

        var spReturnedTextSize = sp.getString("text_size_preferences","24f")
        var convertedReturnedTextSize : Float = spReturnedTextSize!!.toFloat()


        startTextViewMA.textSize = convertedReturnedTextSize
        endTextViewMA.textSize = convertedReturnedTextSize
        goButton.textSize = convertedReturnedTextSize
        endingAddressEditText.textSize = convertedReturnedTextSize
        currentLocationTextView.textSize = convertedReturnedTextSize
        //
        goButton.setOnClickListener {
            startingAddress = currentLocationTextView.text.toString()
            endingAddress = endingAddressEditText.text.toString()
            Toast.makeText(this, endingAddress, Toast.LENGTH_SHORT).show()
            val geocoder = Geocoder(this)
            val results: List<Address>? = geocoder.getFromLocationName(endingAddress, 1)

            if (results != null && results.isNotEmpty()) {
                val addressResult = results[0]
                val latitude = addressResult.latitude
                val longitude = addressResult.longitude

                val mapIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=$latitude,$longitude"))
                mapIntent.setPackage("com.google.android.apps.maps")


                if(mapIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(mapIntent)
                }
                else{
                    Toast.makeText(this, "Please install Google Maps app to continue", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Error finding address", Toast.LENGTH_SHORT).show()
            }

            //Toast.makeText(this@MainActivity, "Successful", Toast.LENGTH_SHORT).show()
        }

        MABottomNavigationBar = findViewById(R.id.bottomNavigationView)

        MABottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.FeedbackItem -> {
                    val goToFeedbackActivity = Intent(this@MainActivity, Feedback::class.java)
                    startActivity(goToFeedbackActivity)
                    finish()
                    true
                }
                R.id.FavoritesItem -> {
                    val goToFavoriteActivity = Intent(this@MainActivity, Favorites::class.java)
                    startActivity(goToFavoriteActivity)
                    finish()
                    true
                }
                R.id.ProfileItem -> {
                    val goToProfileActivity = Intent(this@MainActivity, Profile::class.java)
                    startActivity(goToProfileActivity)
                    finish()
                    true
                }
                else -> {
                    true
                }

            }
        }


        //continue coding here


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.ma_tool_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.settingsMenuItem -> {
                val goToSettingsActivity = Intent(this@MainActivity, Settings::class.java)
                startActivity(goToSettingsActivity)
                //code needed here
                true
            }
            R.id.signOutMenuItem -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getCurrentLocationUser() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),permissionCode)
            return
        }
        val getLocation = fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location ->
            if(location != null){

                currentLocation = location
                Toast.makeText(applicationContext, currentLocation.latitude.toString() + ""+ currentLocation.longitude.toString(), Toast.LENGTH_LONG).show()

                val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
                var currentLocationTextView = findViewById<TextView>(R.id.currentLocationTextView)
                var currentAddress = getAddressName(currentLocation.latitude, currentLocation.longitude)
                currentLocationTextView.text = currentAddress
            }
            else{
                val sp = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)

                var spReturnedMapZoom = sp.getString("map_zoom_preference","15f")
                var convertedReturnedMapZoom : Float = spReturnedMapZoom!!.toFloat()
                val here = LatLng(40.73,-73.4450000)
                val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync { googleMap ->
                    googleMap.addMarker(MarkerOptions().position(here))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(here))
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(here, convertedReturnedMapZoom))
                }
                Log.d("My Tag","Location is null")
            }


        }
    }

    private fun getAddressName(latitude: Double, longitude: Double): String {
        var addressName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var address = geoCoder.getFromLocation(latitude,longitude,3)
        addressName = address!![2].locality
        return addressName
    }

    override fun onMapReady(googleMap: GoogleMap){
        val sp = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)

        var spReturnedMapZoom = sp.getString("map_zoom_preference","15f")
        var convertedReturnedMapZoom : Float = spReturnedMapZoom!!.toFloat()

        val latLng = LatLng(currentLocation.latitude,currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("current location")
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, convertedReturnedMapZoom))
        googleMap?.addMarker(markerOptions)
        Log.d("My Tag","OnMapReady called")

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            permissionCode -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocationUser()
            }
        }
    }


    override fun onSharedPreferenceChanged(sp: SharedPreferences, key: String) {
        // Code that should run when a preference is updated
        // Get the shared preference key value and update the app with it
        Log.d("my tag","onSharedPreferenceChanged called")
        startTextViewMA = findViewById(R.id.startTextViewMA)
        endTextViewMA = findViewById(R.id.EndTextViewMA)
        goButton = findViewById(R.id.goButton)
        endingAddressEditText = findViewById(R.id.endingAddressEditText)
        currentLocationTextView = findViewById(R.id.currentLocationTextView)



        //val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sp.registerOnSharedPreferenceChangeListener(this)
        val sp = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)

        var spReturnedTextSize = sp.getString("text_size_preferences","24f")
        var convertedReturnedTextSize : Float = spReturnedTextSize!!.toFloat()
        var spReturnedMapZoom = sp.getString("map_zoom_preference","15f")
        var convertedReturnedMapZoom : Float = spReturnedMapZoom!!.toFloat()


        startTextViewMA.textSize = convertedReturnedTextSize
        endTextViewMA.textSize = convertedReturnedTextSize
        goButton.textSize = convertedReturnedTextSize
        endingAddressEditText.textSize = convertedReturnedTextSize
        currentLocationTextView.textSize = convertedReturnedTextSize

        getCurrentLocationUser()
    }




}