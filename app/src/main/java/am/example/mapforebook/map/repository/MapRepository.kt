package am.example.mapforebook.map.repository

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
fun createRoadUrl(currentLatLng: LatLng, selectedLatLng: LatLng): String {
    val current = "origin=${currentLatLng.latitude},${currentLatLng.longitude}"
    val selected = "destination=${selectedLatLng.latitude},${selectedLatLng.longitude}"
    val sensor = "sensor=false"
    val mode = "mode=driving"
    val parameters = "$current&$selected&$sensor&$mode"
    val output = "json"
    return "https://maps.googleapis.com/maps/api/directions/$output?$parameters"
}

