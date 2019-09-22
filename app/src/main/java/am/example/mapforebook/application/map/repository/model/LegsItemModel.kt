package am.example.mapforebook.application.map.repository.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
data class LegsItemModel(
    @SerializedName("distance")
    val distance: DistanceModel,

    @SerializedName("duration")
    val duration: DurationModel,

    @SerializedName("end_address")
    val endAddress: String,

    @SerializedName("end_location")
    val endLocation: LatLngModel,

    @SerializedName("start_address")
    val startAddress: String,

    @SerializedName("start_location")
    val startLocation: LatLngModel,

    @SerializedName("steps")
    val steps: List<StepsModel>

) {
    data class DistanceModel(
        @SerializedName("text")
        val text: String,

        /**
         * [value] in meters
         */
        @SerializedName("value")
        val value: Double
    )

    data class DurationModel(
        @SerializedName("text")
        val text: String,

        /**
         * [value] in seconds
         */
        @SerializedName("value")
        val value: Double
    )

    data class StepsModel(
        @SerializedName("distance")
        val distance: DistanceModel,

        @SerializedName("duration")
        val duration: DurationModel,

        @SerializedName("end_location")
        val endLocation: LatLngModel,

        @SerializedName("start_location")
        val startLocation: LatLngModel,

        @SerializedName("travel_mode")
        val travelMode: String,

        @SerializedName("html_instructions")
        val htmlInstructions: String,

        @SerializedName("polyline")
        val polyline: PolylineModel
    ) {
        data class PolylineModel(
            @SerializedName("points")
            val points: String
        )
    }
}