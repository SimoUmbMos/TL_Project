package com.tlproject.omada1.tl_project;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.tlproject.omada1.tl_project.GPSTrack.GPSTracker;
import com.tlproject.omada1.tl_project.Model.User;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GPSTracker gps;
    private double Lat, Long;
    private Bundle extras;
    private User Curruser;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Lat = 0;
        Long = 0;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        extras = getIntent().getExtras();
        String User = extras.getString("User");
        Curruser = new User();
        Curruser.setUser(User);
        TextView usernamedsp = (TextView) findViewById(R.id.username);
        usernamedsp.setText(Curruser.getUsername());
        usernamedsp.setTextColor(Color.WHITE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.INTERNET
                }, 1);
            }
        }
        gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            Lat = gps.getLatitude();
            Long = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }
        gps.stopUsingGPS();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.INTERNET
                }, 1);
            }
        }
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            mMap.setMyLocationEnabled(true);
            LatLng Loc = new LatLng(Lat,Long);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(Loc));
            mMap.animateCamera(zoom);
        /* Add a marker in Sydney
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Lat = 0;
                    Long = 0;
                    gps = new GPSTracker(this);
                    if (gps.canGetLocation()) {
                        Lat = gps.getLatitude();
                        Long = gps.getLongitude();
                    } else {
                        gps.showSettingsAlert();
                    }
                    gps.stopUsingGPS();
                } else {
                    gps.showSettingsAlert();
                }
                break;
        }
    }

    public void Logout(View view) {
        Intent intent=new Intent(MapsActivity.this,LoginActivity.class);
        finish();
        startActivity(intent);
    }

    public void ProfileClick(View view) {
        Intent intent=new Intent(MapsActivity.this,ProfileActivity.class);
        intent.putExtra("User", Curruser.ToString());
        startActivity(intent);
    }
}
