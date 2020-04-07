package com.example.transporttracker

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check GPS is enabled
        val lm =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_location_off_black_24dp)

            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show()
        }else{
            findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_location_on_black_24dp)
        }

        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST
            )
        }
    }

    private fun startTrackerService() {
        startService(Intent(this, TrackerService::class.java))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Start the service when the permission is granted
            startTrackerService()
        } else {
            finish()
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST = 1
    }
}