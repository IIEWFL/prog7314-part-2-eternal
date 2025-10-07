package com.example.myapplication.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.model.Property
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.network.ApiService
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null

    // Ask permissions dynamically
    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perms ->
            val granted = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            if (granted) enableMyLocation()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Toolbar setup
        val toolbar: MaterialToolbar = findViewById(R.id.mapToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Map fragment
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true

        ensureLocationPermission()

        // ✅ Center the map on Benoni by default
        val benoni = LatLng(-26.1887, 28.3083)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(benoni, 12f))

        // ✅ Load property data from REST API
        loadPropertiesFromApi()
    }

    private fun loadPropertiesFromApi() {
        val api = RetrofitClient.instance.create(ApiService::class.java)

        // Simple call with no filters
        api.getProperties(0, 10000000, null, null, null)
            .enqueue(object : Callback<List<Property>> {
                override fun onResponse(
                    call: Call<List<Property>>,
                    response: Response<List<Property>>
                ) {
                    if (response.isSuccessful) {
                        val propertyList = response.body() ?: emptyList()

                        if (propertyList.isEmpty()) {
                            Toast.makeText(
                                this@MapActivity,
                                "No properties found",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            for (property in propertyList) {
                                addPropertyMarker(property)
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@MapActivity,
                            "Failed to load properties (Error ${response.code()})",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<Property>>, t: Throwable) {
                    Toast.makeText(
                        this@MapActivity,
                        "Error loading properties: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun addPropertyMarker(property: Property) {
        // For simplicity, assign approximate coordinates based on location name
        val locationCoords = when (property.location.lowercase()) {
            "sandton" -> LatLng(-26.1076, 28.0567)
            "soweto" -> LatLng(-26.2485, 27.8540)
            "randburg" -> LatLng(-26.1376, 27.9736)
            "benoni" -> LatLng(-26.1887, 28.3083)
            else -> LatLng(-26.1887, 28.3083) // Default: Benoni
        }

        googleMap?.addMarker(
            MarkerOptions()
                .position(locationCoords)
                .title(property.title)
                .snippet("R${property.price} · ${property.location}")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )
    }

    private fun ensureLocationPermission() {
        val fineGranted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineGranted || coarseGranted) {
            enableMyLocation()
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap?.isMyLocationEnabled = true

            val fused = LocationServices.getFusedLocationProviderClient(this)
            fused.lastLocation.addOnSuccessListener { loc ->
                loc?.let {
                    val here = LatLng(it.latitude, it.longitude)
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(here, 14f))
                }
            }
        }
    }
}

/**

 * Android Developers, 2024. *Working with Permissions in Android*. [online]
 * Available at: <https://developer.android.com/training/permissions/requesting>

 * Android Developers, 2024. *Location Access and User Privacy in Android*. [online]
 * Available at: <https://developer.android.com/training/location>

 * Google Developers, 2024. *Using Google Maps SDK for Android*. [online]
 * Available at: <https://developers.google.com/maps/documentation/android-sdk/start>

 * Google Maps Platform, 2024. *Maps SDK for Android: Marker and Map Controls*. [online]
 * Available at: <https://developers.google.com/maps/documentation/android-sdk/marker>

 * Google Developers, 2024. *Location Services with FusedLocationProviderClient*. [online]
 * Available at: <https://developers.google.com/location-context/fused-location-provider>

 * Square, 2024. *Retrofit: Type-Safe HTTP Client for Android and Java*. [online]
 * Available at: <https://square.github.io/retrofit/>

 */
