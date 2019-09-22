package am.example.mapforebook.application.map.viewmodel

import am.example.mapforebook.application.map.repository.MapRepository
import am.example.mapforebook.application.map.repository.model.LegsItemModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Ara Hakobyan on 9/21/19.
 * company IDT
 */
class MapFragmentViewModel : ViewModel() {

    val repository = MapRepository.instance

    val startLocationLiveData: MutableLiveData<LatLng> = MutableLiveData()

    val selectedLatLng: MutableLiveData<LatLng> = MutableLiveData()

    val stepsLiveData: MutableLiveData<LegsItemModel> = MutableLiveData()

    fun getRoute(toLatLng: LatLng, selectedLatLng: LatLng, string: String, stepsLiveData: MutableLiveData<LegsItemModel>) {
        repository.getRoute(toLatLng, selectedLatLng,string, stepsLiveData )
    }
}