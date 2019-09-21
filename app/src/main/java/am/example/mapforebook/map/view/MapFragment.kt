package am.example.mapforebook.map.view

import am.example.mapforebook.R
import am.example.mapforebook.base.view.BaseFragment
import am.example.mapforebook.core.extencions.toLatLng
import am.example.mapforebook.map.repository.DownloadTask
import am.example.mapforebook.map.repository.createRoadUrl
import am.example.mapforebook.map.viewmodel.MapActivityViewModel
import am.example.mapforebook.map.viewmodel.MapFragmentViewModel
import android.location.Location
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.map_fragment_layout.*

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
            createRoad(activityViewModel.currentLocationLiveData.value, it)
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
        showCurrentLocation(activityViewModel.currentLocationLiveData.value?: return true)
        return true
    }

    private fun showCurrentLocation(location: Location) {
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(location.toLatLng(), 15f))
    }

    private fun createRoad(currentLocation: Location?, selectedLatLng: LatLng) {
        currentLocation?.let {
            var downloadTask: DownloadTask = DownloadTask()
            downloadTask.execute(createRoadUrl(it.toLatLng(), selectedLatLng))
        }
    }
}