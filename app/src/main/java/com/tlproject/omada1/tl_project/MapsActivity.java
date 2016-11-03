package com.tlproject.omada1.tl_project;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tlproject.omada1.tl_project.GPSTrack.GPSTracker;
import com.tlproject.omada1.tl_project.Model.User;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GPSTracker gps;
    private double Lat, Long;
    private User Curruser;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Lat = 0;
        Long = 0;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Bundle extras = getIntent().getExtras();
        String User = extras.getString("User");
        Curruser = new User();
        Curruser.setUser(User);
        TextView usernamedsp = (TextView) findViewById(R.id.username);
        usernamedsp.setText(Curruser.getUsername());
        usernamedsp.setTextColor(Color.WHITE);
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
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        mMap.setMyLocationEnabled(true);
        LatLng Loc = new LatLng(Lat,Long);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Loc));
        mMap.animateCamera(zoom);
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
    void setquestonmap(double lat,double lng){
        mMap.clear();
        mMap.addCircle(new CircleOptions().center(new LatLng(lat,lng)).radius(15).strokeColor(Color.RED).fillColor(Color.BLUE));
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("Current Quest"));
    }
    float distof(double lat1,double lng1,double lat2,double lng2){
        Location loc1,loc2;
        loc1=new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lng1);
        loc2=new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lng2);
        return loc1.distanceTo(loc2);
    }
}
