package am.example.mapforebook.network

import am.example.mapforebook.application.map.repository.model.RouteResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
interface NetworkApi {

    @GET("directions/json")
    fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ): Call<RouteResponseModel>
}