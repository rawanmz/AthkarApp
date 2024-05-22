package com.example.athkarapp.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapView() {

    val context = LocalContext.current
    val geofenceHelper = GeofenceHelper(context)
    val geofence = geofenceHelper.createGeofence("GEOFENCE_ID", 24.743210, 46.637042, 5f)
    geofenceHelper.addGeofence(
        context,
        geofence,
        onSuccess = {
            Toast.makeText(context, "Geofence added", Toast.LENGTH_SHORT).show()
            Toast.makeText(context, "${geofence.expirationTime}", Toast.LENGTH_SHORT).show()

        },
        onFailure = { error -> /* Handle failure */
            Toast.makeText(context, "Geofence not added", Toast.LENGTH_SHORT).show()

        })
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(geofence.latitude,geofence.longitude),14f)
    }
    var uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true, myLocationButtonEnabled = true))
    }

    GoogleMap(
        modifier = Modifier.size(400.dp),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        properties = MapProperties(isMyLocationEnabled = true)
    ) {

        Marker(
            state = MarkerState(LatLng(geofence.latitude,geofence.longitude)),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )

        Circle(center = LatLng(geofence.latitude,geofence.longitude), radius = geofence.radius.toDouble(), fillColor = Color.LightGray)
    }
}