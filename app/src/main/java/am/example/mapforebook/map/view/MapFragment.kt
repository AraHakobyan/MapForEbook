package am.example.mapforebook.map.view

import am.example.mapforebook.R
import am.example.mapforebook.base.view.BaseFragment
import am.example.mapforebook.map.viewmodel.MapActivityViewModel
import am.example.mapforebook.map.viewmodel.MapFragmentViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.map_fragment_layout.*

/**
 * Created by Ara Hakobyan on 9/21/19.
 * company IDT
 */
class MapFragment : BaseFragment<MapActivityViewModel, MapFragmentViewModel>(), OnMapReadyCallback {

    override fun onCreateView(): Int = R.layout.map_fragment_layout

    override fun setupView() {
        setupMapView()
    }

    override fun initFragmentViewModel() {
        fragmentViewModel = ViewModelProviders.of(this).get(MapFragmentViewModel::class.java)
    }

    override fun initActivityViewModel() {
        activityViewModel = ViewModelProviders.of(requireActivity()).get(MapActivityViewModel::class.java)
    }

    private fun setupMapView() {
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
    }
}