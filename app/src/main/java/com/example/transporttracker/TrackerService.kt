package com.example.transporttracker

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.Manifest;
import android.app.Notification
import android.os.Build
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class TrackerService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        loginToFirebase()
        requestLocationUpdates()
    }


    private fun loginToFirebase() {
        // Authenticate with Firebase, and request location updates
        val email = getString(R.string.firebase_email)
        val password = getString(R.string.firebase_password)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email, password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "firebase auth success")
                requestLocationUpdates()
            } else {
                Log.d(TAG, "firebase auth failed")
            }
        }
    }


    private fun requestLocationUpdates() {
        val request = LocationRequest()
        request.interval = 10000
        request.fastestInterval = 5000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val client =
            LocationServices.getFusedLocationProviderClient(this)
        val path =
            getString(R.string.firebase_path) + "/" + getString(R.string.transport_id)
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val ref = FirebaseDatabase.getInstance().getReference(path)
                    val location: Location? = locationResult.lastLocation
                    if (location != null) {
                        Log.d(TAG, "location update $location")
                        ref.setValue(location)
                    }
                }
            }, null)
        }
    }


    companion object {
        private val TAG = TrackerService::class.java.simpleName
    }
}