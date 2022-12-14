package org.wit.a20090170_assignment2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import org.wit.a20090170_assignment2.R
import org.wit.a20090170_assignment2.databinding.ActivityRentalCarsMapsBinding
import org.wit.a20090170_assignment2.databinding.ContentRentalCarsMapsBinding
import org.wit.a20090170_assignment2.main.MainApp

class RentalCarsMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityRentalCarsMapsBinding
    private lateinit var contentBinding: ContentRentalCarsMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRentalCarsMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        contentBinding = ContentRentalCarsMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        // add a marker for each rental car
        app.rentalCars.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.brand).position(loc)
            map.addMarker(options)?.tag = it.id
            map.setOnMarkerClickListener(this)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        contentBinding.currentBrand.text = marker.title

        //i("ID'S" + marker.tag + " " + marker.id)

        app.rentalCars.findAll().forEach {
            //i("CAR ID" + it.id.toString() + " " + marker.tag)
            if (marker.tag == it.id) {
                //i("FOUND IT")
                contentBinding.currentYear.text = it.year.toString()
                Picasso.get().load(it.image).into(contentBinding.imageView)
            }
        }

        return false
    }
}