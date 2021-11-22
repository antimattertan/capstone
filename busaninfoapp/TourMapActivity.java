package com.example.busaninfoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TourMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = TourMapActivity.class.getSimpleName();

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_tour_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d(TAG, "onMapReady :");
        mMap = googleMap;

        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        showMarker();
    }

    private void showMarker() {
        Intent intent = getIntent();
        Tour tour = (Tour) intent.getSerializableExtra("tourInfo");

        LatLng markerLatLng = new LatLng(tour.getLat(), tour.getLon());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(markerLatLng);
        markerOptions.title(tour.getMainTitle());
        markerOptions.draggable(true);

        mMap.addMarker(markerOptions).showInfoWindow();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(markerLatLng, 16);
        mMap.moveCamera(cameraUpdate);
    }
}