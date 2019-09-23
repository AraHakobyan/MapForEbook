package am.example.mapforebook.application.map.view

import am.example.mapforebook.R
import am.example.mapforebook.application.map.base.view.BaseMapActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.map_avtivity_layout.*

/**
 * Created by Ara Hakobyan on 9/21/19.
 * company IDT
 */
class MapActivity : BaseMapActivity() {
    override fun onCreateView(): Int = R.layout.map_avtivity_layout

    override fun setupView() {
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.mapFragment -> {
                    toolbar.run {
                        title = getString(R.string.app_name)
                        setBackgroundResource(R.color.colorPrimary)
                    }
                }
                R.id.introFragment -> {
                    toolbar.run {
                        title = getString(R.string.some_into)
                        setBackgroundResource(R.color.colorPrimaryDark)
                    }
                }
            }
        }
    }

    fun openMapFragment(@Suppress("UNUSED_PARAMETER") view: View) {
        findNavController(R.id.nav_host_fragment).navigate(R.id.to_map_fragment)
    }
}