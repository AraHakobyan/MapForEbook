package am.example.mapforebook.map.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Ara Hakobyan on 9/21/19.
 * company IDT
 */
class MapActivityViewModel : ViewModel() {
    val currentLocationLiveData: MutableLiveData<Location> = MutableLiveData()
}