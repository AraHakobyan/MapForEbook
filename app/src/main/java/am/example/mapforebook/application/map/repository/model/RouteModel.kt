package am.example.mapforebook.application.map.repository.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
data class RouteModel(
    @SerializedName("bounds")
    var bounds: BoundsModel,

    @SerializedName("copyrights")
    val copyrights: String,

    @SerializedName("legs")
    val legs: List<LegsItemModel>
)