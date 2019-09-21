package am.example.mapforebook.core.extencions

import android.location.Location
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
fun Location.toLatLng(): LatLng = LatLng(latitude, longitude)