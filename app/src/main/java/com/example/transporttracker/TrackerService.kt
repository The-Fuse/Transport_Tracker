package com.example.transporttracker

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.Manifest
import android.app.*
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService

class TrackerService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        buildnotification()
        requestLocationUpdates()
    }

    private fun buildnotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val collapsedview = RemoteViews(
                packageName,
                R.layout.notification_collapse
            )
            val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId="com.example.transporttracker"
            val name=getString(R.string.app_name)
            val description="Tracking your location Tap to cancel."
            val importance=NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId,name,importance).apply {
                setDescription(description)}
            notificationManager.createNotificationChannel(channel)
            val builder =NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.ic_tracker)
                .setCustomContentView(collapsedview)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            with(NotificationManagerCompat.from(this)){
                notify(1,builder.build())
            }
        } else {
            TODO("VERSION.SDK_INT < N")
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