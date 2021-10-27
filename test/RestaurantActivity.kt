package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.clustering.ClusterManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_restaurant.*;
import kotlinx.android.synthetic.main.search_bar.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class RestaurantActivity : AppCompatActivity() {
    // 권한 필요한 퍼미션 목록
    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    // 퍼미션 승인 요청시 사용하는 코드
    val REQUEST_PERMISSION_CORE = 1

    // 기본 맵 줌 레벨
    val DEFAULT_ZOOM_LEVEL = 14f

    // 위치 정보를 불러올 수 없는 경우 부산 시청의 위치 정보를 가져와 화면에 표시
    val CITY_HALL = LatLng(35.17914523506671, 129.07492106277513)

    val googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        mapView.onCreate(savedInstanceState)



        if(hasPermissions()) {
            initMap()
        }
        else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CORE)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        initMap()

    }

    fun hasPermissions(): Boolean {

        for(permission in PERMISSIONS) {
            if(ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    val itemMap = mutableMapOf<JSONObject, MyItem>()

    @SuppressLint("MissingPermission")
    fun initMap() {

        mapView.getMapAsync {


            it.uiSettings.isMyLocationButtonEnabled = false
            when {
                hasPermissions() -> {
                    it.isMyLocationEnabled = true
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
                    val json = assets.open("jsons/busaninfoapp-restaurant.json").reader().readText()
                    val jsonArray = JSONArray(json)
                    val textList = mutableListOf<String>()
                    for (i in 0 until jsonArray.length()) {
                        val restaurant = jsonArray.getJSONObject(i)
                        textList.add(restaurant.getString("bsnsNm"))
                        val lat = restaurant.getDouble("lat")
                        val lng = restaurant.getDouble("lng")
                        val mark = LatLng(lat, lng)
                        it.addMarker(
                            MarkerOptions().position(mark)
                                .title(restaurant.getString("bsnsNm"))
                        )

                        val item = MyItem(
                            LatLng(mark.latitude, mark.longitude),
                            restaurant.getString("bsnsNm")
                        )

                        itemMap.put(restaurant, item)
                    }
                    //자동완성 텍스트뷰에서 사용하는 어댑터
                    val adapter = ArrayAdapter<String>(
                        this@RestaurantActivity,
                        android.R.layout.simple_dropdown_item_1line, textList
                    )
                    // 자동완성이 시작되는 글자수 지정
                    searchBar.autoCompleteTextView.threshold = 1
                    searchBar.autoCompleteTextView.setAdapter(adapter)
                }
                else -> {
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, DEFAULT_ZOOM_LEVEL))
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation(): LatLng{
        val locationProvider: String = LocationManager.GPS_PROVIDER
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val lastLocation: Location? = locationManager.getLastKnownLocation(locationProvider)
        if(lastLocation != null) {
            return LatLng(lastLocation.latitude, lastLocation.longitude)
        }
        else {
            return CITY_HALL
        }
    }

    fun searchMyLocation() {
        when {
            hasPermissions() -> googleMap?.moveCamera(CameraUpdateFactory.
            newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
            else -> Toast.makeText(applicationContext, "위치사용권한 설정에 동의해주세요", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    fun JSONArray.findByChildProperty(propertyName: String, value:String): JSONObject?{
        for(i in 0 until length()) {
            val obj = getJSONObject(i)
            if (value == obj.getString(propertyName)) return obj
        }
        return null
    }

    override fun onStart() {
        super.onStart()
        LocationSerButton.setOnClickListener { searchMyLocation()}

        val restaurantJson = assets.open("jsons/busaninfoapp-restaurant.json").reader().readText()
        val restaurants = JSONArray(restaurantJson)
        searchBar.imageView.setOnClickListener {
            val keyword = searchBar.autoCompleteTextView.text.toString()
            if(TextUtils.isEmpty(keyword)) return@setOnClickListener


            restaurants.findByChildProperty("bsnsNm", keyword)?.let {
                var restLat = it.getDouble("lat")
                var restLng = it.getDouble("lng")
                googleMap?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(restLat, restLng), DEFAULT_ZOOM_LEVEL))
            }
            searchBar.autoCompleteTextView.setText("")
        }
    }
}










































