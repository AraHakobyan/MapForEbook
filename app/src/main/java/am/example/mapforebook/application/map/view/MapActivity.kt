package am.example.mapforebook.application.map.view

import am.example.mapforebook.R
import am.example.mapforebook.application.map.base.view.BaseMapActivity
import android.view.View
import androidx.navigation.findNavController

/**
 * Created by Ara Hakobyan on 9/21/19.
 * company IDT
 */
class MapActivity : BaseMapActivity() {
    override fun onCreateView(): Int = R.layout.map_avtivity_layout

    override fun setupView() {

    }

    fun openMapFragment(view: View) {
        findNavController(R.id.nav_host_fragment).navigate(R.id.to_map_fragment)
    }
}