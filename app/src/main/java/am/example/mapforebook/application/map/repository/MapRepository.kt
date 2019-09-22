package am.example.mapforebook.application.map.repository

import am.example.mapforebook.core.extencions.asStringFormat
import am.example.mapforebook.application.map.repository.model.LegsItemModel
import am.example.mapforebook.application.map.repository.model.RouteResponseModel
import am.example.mapforebook.network.API_SERVICE
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
class MapRepository {

    fun getRoute(
        currentLatLng: LatLng,
        selectedLatLng: LatLng,
        key: String,
        legsLiveData: MutableLiveData<LegsItemModel>
    ) {
        val byCoordinatesCall: Call<RouteResponseModel> =
            API_SERVICE.getDirections(
                origin = currentLatLng.asStringFormat(),
                destination = selectedLatLng.asStringFormat(),
                key = key
            )
        byCoordinatesCall.enqueue(object : Callback<RouteResponseModel>{
            override fun onFailure(call: Call<RouteResponseModel>, t: Throwable) = Unit

            override fun onResponse(
                call: Call<RouteResponseModel>,
                response: Response<RouteResponseModel>
            ) {
                if (response.isSuccessful) {
                    legsLiveData.postValue(response.body()?.routes?.get(0)?.legs?.get(0))
                }
            }
        })
    }

    private object Holder {
        val INSTANCE = MapRepository()
    }

    companion object {

        /**
         * the instance of Map repository
         */
        val instance: MapRepository by lazy { Holder.INSTANCE }

    }
}

