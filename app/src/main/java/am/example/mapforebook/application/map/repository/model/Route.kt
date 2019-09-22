package am.example.mapforebook.application.map.repository.model

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
data class Route(val startName: String = "",
                 val endName: String = "",
                 val startLat: Double?,
                 val startLng: Double?,
                 val endLat: Double?,
                 val endLng: Double?,
                 val overviewPolyline: String = "")