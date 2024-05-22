package com.example.athkarapp.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.*
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

const val ACTION_GEOFENCE_EVENT = "ACTION_PROCESS_GEOFENCE_TRANSITION"

class GeofenceHelper(context: Context) {
    private val geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun addGeofence(context: Context,geofence: Geofence, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val geofencingRequest = GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER or GeofencingRequest.INITIAL_TRIGGER_EXIT)
            addGeofence(geofence)
        }.build()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).run {
            addOnSuccessListener {
                onSuccess()
            }
            addOnFailureListener { e ->
                val errorMessage = GeofenceStatusCodes.getStatusCodeString(e.hashCode())
                onFailure(errorMessage)
            }
        }
    }

    fun createGeofence(id: String, lat: Double, lon: Double, radius: Float): Geofence {
        return Geofence.Builder()
            .setRequestId(id)
            .setCircularRegion(lat, lon, radius)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()
    }
}


class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent?.hasError() == false) {
            val errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
            Log.e("GeofenceBroadcastReceiver", "Geofence error: $errorMessage")
            Toast.makeText(context, "Geofence error: $errorMessage", Toast.LENGTH_LONG).show()
            return
        }

        val geofenceTransition = geofencingEvent?.geofenceTransition
        val triggeringGeofences = geofencingEvent?.triggeringGeofences

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Toast.makeText(context, "Entered geofence area", Toast.LENGTH_SHORT).show()
            // Handle the event - user entered the geofence
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Toast.makeText(context, "Exited geofence area", Toast.LENGTH_SHORT).show()

            // Handle the event - user exited the geofence
        } else {
            // Handle other geofence transitions if needed
            Toast.makeText(context, "Unknown geofence transition", Toast.LENGTH_SHORT).show()

        }

        triggeringGeofences?.forEach { geofence ->
            val requestId = geofence.requestId
            Toast.makeText(
                context,
                "Geofence transition for requestId:$requestId",
                Toast.LENGTH_SHORT
            ).show()

            // Perform any additional processing based on the geofence requestId
        }
    }
}
