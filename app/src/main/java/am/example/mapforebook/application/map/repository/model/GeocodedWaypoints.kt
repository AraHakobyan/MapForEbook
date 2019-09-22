package am.example.mapforebook.application.map.repository.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
data class GeocodedWaypoints(
    @SerializedName("geocoder_status")
    val status: String,

    @SerializedName("place_id")
    val placeId: String,

    @SerializedName("types")
    val types: List<String>
)