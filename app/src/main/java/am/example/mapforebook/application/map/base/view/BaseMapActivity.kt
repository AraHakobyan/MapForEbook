package am.example.mapforebook.application.map.base.view

import am.example.mapforebook.application.base.view.BaseActivity
import am.example.mapforebook.application.map.viewmodel.MapActivityViewModel
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
abstract class BaseMapActivity : BaseActivity<MapActivityViewModel>() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location: Location? = null

    override fun initActivityViewModel() {
        activityViewModel = ViewModelProviders.of(this).get(MapActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        checkPermission()
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationClient.lastLocation.addOnSuccessListener {
                        location = it
                        activityViewModel.currentLocationLiveData.postValue(it)
                    }
                }
            }
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_REQUEST_CODE
            )
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                location = it
                activityViewModel.currentLocationLiveData.postValue(it)
            }
        }
    }
}

private const val LOCATION_REQUEST_CODE = 1000