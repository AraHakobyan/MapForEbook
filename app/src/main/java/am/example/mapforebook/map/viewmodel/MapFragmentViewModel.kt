package am.example.mapforebook.map.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Ara Hakobyan on 9/21/19.
 * company IDT
 */
class MapFragmentViewModel : ViewModel() {

    val selectedLatLng: MutableLiveData<LatLng> = MutableLiveData()

//    fun getRoad(currentLocation: Location, selectedLocation: Location) = MapRepository.getRoad()
}