package com.example.busaninfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ParkingLotActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Location lastLocation;
    private LocationManager locationManager;

    private static final String parkFile = "jsons/parkingLots.json";

    private static final String[] PERMISSIONS =
            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    final LatLng CITY_HALL = new LatLng(35.17914523506671, 129.07492106277513);
    private GoogleMap parkingMap;
    ArrayList<MyParkingLot> parkingLots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapPark);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        parkingMap = googleMap;

        if (getLocationPermission()) {
            initMap(parkingMap);
        }
        else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE);
        }

        Toast.makeText(getApplicationContext(), "길이: " + parkingLots.size(), Toast.LENGTH_LONG).show();
        parkingMap.setOnMyLocationButtonClickListener(this);
        parkingMap.setOnMyLocationClickListener(this);

    }

    // 퍼미션 확인부분
    private Boolean getLocationPermission() {
        for (int i = 0; i < 2; i++) {
            String permission = PERMISSIONS[i];
            if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    public void initMap(GoogleMap googleMap) {

        if (getLocationPermission()) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            addMarker(googleMap);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), 12));
        }
        else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, 12));
        }
    }

    @SuppressLint("MissingPermission")
    public LatLng getMyLocation() {
        String locationProvider = LocationManager.GPS_PROVIDER;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lastLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastLocation != null) {
            return new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        }
        else {
            return CITY_HALL;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }


    public JSONArray loadJson(String jsonFile) {
        String bf = "";

        JSONArray parkingLot = null;
        try {
            InputStream is = getAssets().open(jsonFile);

            int FileSize = is.available();
            byte[] buffer = new byte[FileSize];
            is.read(buffer);
            is.close();

            bf = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(bf);

            parkingLot = jsonObject.getJSONArray("item");

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parkingLot;
    }

    public static Location addrToPoint(Context context, String addr) {
        Location location = new Location("");
        Geocoder geocoder = new Geocoder(context);
        List<Address> address = null;

        try {
            address = geocoder.getFromLocationName(addr, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(address != null) {
            for(int i = 0; i < address.size(); i++) {
                Address a = address.get(i);
                location.setLatitude(a.getLatitude());
                location.setLongitude(a.getLongitude());
            }
        }
        return location;
    }


    public void addMarker(GoogleMap googleMap) {
        JSONArray parkingLot = loadJson(parkFile);
        String addr;
        Location l;
        try {
            for (int i = 0; i < parkingLot.length(); i++) {
                JSONObject parking = parkingLot.getJSONObject(i);
                MyParkingLot item = new MyParkingLot(parking.getString("pkNam"), parking.getString("jibunAddr"),
                        parking.getString("tponNum"), parking.getString("pkGubun"), parking.getString("pkCnt"),
                        parking.getString("svcSrtTe"), parking.getString("svcEndTe"), parking.getString("satSrtTe"), parking.getString("satEndTe"),
                        parking.getString("hldSrtTe"), parking.getString("hldEndTe"), parking.getString("tenMin"), parking.getString("ftDay"),
                        parking.getString("oprDay"), parking.getString("pkBascTime"), parking.getString("pkAddTime"), parking.getString("feeAdd"),
                        parking.getString("payMtd"),parking.getString("doroAddr"));

                addr = item.getPkAddr();
                if (addr.equals('-')) {
                    addr = item.getDoroAddr();
                    if (addr.equals('-')) { }
                    else {
                        l = addrToPoint(this.getApplicationContext(),addr);
                        item.setLatP(l.getLatitude());
                        item.setLngP(l.getLongitude());
                    }
                }
                else {
                    l = addrToPoint(this.getApplicationContext(),addr);
                    item.setLatP(l.getLatitude());
                    item.setLngP(l.getLongitude());
                }

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(item.getLatP(), item.getLngP()));
                markerOptions.title(item.getPkName());
                googleMap.addMarker(markerOptions);
                parkingLots.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ParkingLotTask extends AsyncTask<Void, JSONArray, String> {

        @Override
        public void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }
    }
}