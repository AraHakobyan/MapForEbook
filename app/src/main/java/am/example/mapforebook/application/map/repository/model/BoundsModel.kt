package am.example.mapforebook.application.map.repository.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
data class BoundsModel(
    @SerializedName("northeast")
    val northeast: LatLngModel,

    @SerializedName("southwest")
    val southwest: LatLngModel
)