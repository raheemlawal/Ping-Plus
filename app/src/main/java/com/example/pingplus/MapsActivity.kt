package com.example.pingplus

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var model: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        model = this.run {
            ViewModelProviders.of(this).get(ViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }
    override fun onMapReady(googleMap: GoogleMap) {

        val locData = intent.extras
        val loc = locData?.getString("R")

        mMap = googleMap

        val geocoder = Geocoder(this, Locale.getDefault())
        val location = loc
        val addresses: List<Address> = geocoder.getFromLocationName(location, 1)
        val place = LatLng(addresses[0].latitude,addresses[0].longitude)
        mMap.addMarker(MarkerOptions().position(place).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place))
    }
}