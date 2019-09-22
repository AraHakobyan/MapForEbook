package am.example.mapforebook.application.map.repository.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
data class RouteResponseModel(
    @SerializedName("geocoded_waypoints")
    val waypoints: List<GeocodedWaypoints>,

    @SerializedName("routes")
    val routes: List<RouteModel>,

    @SerializedName("status")
    val status: String
)