package am.example.mapforebook.application.map.base.view

import am.example.mapforebook.application.base.view.BaseActivity
import am.example.mapforebook.application.map.viewmodel.MapActivityViewModel
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.content.Intent
import android.provider.Settings
import android.location.LocationManager

/**
 * Created by Ara Hakobyan on 9/22/19.
 * company IDT
 */
abstract class BaseMapActivity : BaseActivity<MapActivityViewModel>() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location: Location? = null
    private lateinit var locationManager: LocationManager
    private var mLocationListeners = arrayOf(
        LocationListener(LocationManager.GPS_PROVIDER),
        LocationListener(LocationManager.NETWORK_PROVIDER)
    )

    override fun initActivityViewModel() {
        activityViewModel = ViewModelProviders.of(this).get(MapActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initializeLocationManager()
        checkPermission()
        turnGPSOn()
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

    private fun turnGPSOn() {
        if (!locationManager.isProviderEnabled("gps")) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    private fun initializeLocationManager() {
        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                mLocationListeners[1]
            )
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                mLocationListeners[0]
            )
        } catch (ex: SecurityException) {}

    }

    private inner class LocationListener(@Suppress("UNUSED_PARAMETER") provider: String) : android.location.LocationListener {

        override fun onLocationChanged(location: Location) {
            activityViewModel.currentLocationLiveData.postValue(location)
        }

        override fun onProviderDisabled(provider: String) = Unit
        override fun onProviderEnabled(provider: String) = Unit
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) = Unit
    }
}

private const val LOCATION_REQUEST_CODE = 1000
private const val LOCATION_INTERVAL: Long = 1000
private const val LOCATION_DISTANCE: Float = 30f