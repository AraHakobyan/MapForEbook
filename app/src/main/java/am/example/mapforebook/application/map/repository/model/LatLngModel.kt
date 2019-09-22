package am.example.mapforebook.application.map.repository.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
data class LatLngModel(
    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lng")
    val lng: Double
)