package am.example.mapforebook.application.map.view

import am.example.mapforebook.R
import am.example.mapforebook.application.base.view.BaseFragment
import am.example.mapforebook.core.extencions.toLatLng
import am.example.mapforebook.application.map.repository.model.LegsItemModel
import am.example.mapforebook.application.map.viewmodel.MapActivityViewModel
import am.example.mapforebook.application.map.viewmodel.MapFragmentViewModel
import android.graphics.Color
import android.location.Location
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.map_fragment_layout.*
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import android.content.Context
import android.graphics.Canvas
import com.google.maps.android.PolyUtil


/**
 * Created by Ara Hakobyan on 9/21/19.
 * company IDT
 */
class MapFragment : BaseFragment<MapActivityViewModel, MapFragmentViewModel>(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener {

    private var map: GoogleMap? = null

    override fun onCreateView(): Int = R.layout.map_fragment_layout

    override fun setupView() {
        setupMapView()
        activityViewModel.currentLocationLiveData.observe(
            this,
            Observer(::onCurrentLocationChanged)
        )
        fragmentViewModel.selectedLatLng.observe(this, Observer {
            createRoad(activityViewModel.currentLocationLiveData.value ?: return@Observer, it)
        })

        fragmentViewModel.stepsLiveData.observe(this, Observer{
            addMarkers(fragmentViewModel.startLocationLiveData.value!!,fragmentViewModel.selectedLatLng.value!!, it.startAddress, it.endAddress )
            drawRouteOnMap(it)
        })
    }

    private fun addMarkers(currentLocation: LatLng, selectedLatLng: LatLng, startAddress: String, endAddress: String) {
        map?.run {
            clear()
            addMarker(
                MarkerOptions().position(currentLocation).icon(
                    bitmapDescriptorFromVector(context!!, R.drawable.ic_start_location)
                ).title(startAddress).also { _: MarkerOptions? ->
                    setOnMarkerClickListener {
                        it.showInfoWindow()
                        true
                    }
                }
            )
            addMarker(
                MarkerOptions().position(selectedLatLng).icon(
                    bitmapDescriptorFromVector(context!!, R.drawable.ic_end_location)
                ).title(endAddress).also { _: MarkerOptions? ->
                    setOnMarkerClickListener {
                        it.showInfoWindow()
                        true
                    }
                }
            )
        }
    }

    private fun drawRouteOnMap(legs: LegsItemModel) {
        val latLngList: MutableList<LatLng> = mutableListOf()
        latLngList.add(fragmentViewModel.startLocationLiveData.value!!)
        legs.steps.forEach {
            latLngList.addAll(PolyUtil.decode(it.polyline.points))
        }

        map?.addPolyline(PolylineOptions().apply {
            geodesic(true)
            addAll(latLngList)
            width(10f)
            color(Color.BLACK)
        })
    }

    override fun initFragmentViewModel() {
        fragmentViewModel = ViewModelProviders.of(this).get(MapFragmentViewModel::class.java)
    }

    override fun initActivityViewModel() {
        activityViewModel =
            ViewModelProviders.of(requireActivity()).get(MapActivityViewModel::class.java)
    }

    private fun onCurrentLocationChanged(location: Location) {
        map?.let { showCurrentLocation(location) }
    }

    private fun setupMapView() {
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        googleMap.run {
            isMyLocationEnabled = true
            setOnMyLocationButtonClickListener(this@MapFragment)
            map = this
            setOnMapLongClickListener {
                fragmentViewModel.selectedLatLng.postValue(it)
            }
        }
        activityViewModel.currentLocationLiveData.value?.let(::showCurrentLocation)
    }

    override fun onMyLocationButtonClick(): Boolean {
        showCurrentLocation(activityViewModel.currentLocationLiveData.value ?: return true)
        return true
    }

    private fun showCurrentLocation(location: Location) {
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(location.toLatLng(), 15f))
    }

    private fun createRoad(currentLocation: Location, selectedLatLng: LatLng) {
        fragmentViewModel.startLocationLiveData.value = LatLng(currentLocation.latitude, currentLocation.longitude)
        fragmentViewModel.getRoute(
            currentLocation.toLatLng(),
            selectedLatLng,
            getString(R.string.google_map_key),
            fragmentViewModel.stepsLiveData
        )
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}