package com.tlproject.omada1.tl_project.Activitys;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.tlproject.omada1.tl_project.Controller.CheckController;
import com.tlproject.omada1.tl_project.Controller.QuestController;
import com.tlproject.omada1.tl_project.Controller.UserController;
import com.tlproject.omada1.tl_project.GPSTrack.GPSTracker;
import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;
import com.tlproject.omada1.tl_project.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private int QuestOnMapRadius=60;
    private double Lat, Long;
    private GoogleMap mMap;
    private UserController CurUController;
    private QuestController CurQController;
    private User CurUser;
    private Quest CurQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
            init();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setquestonmap(CurQuest.getLat(), CurQuest.getLng());
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        mMap.setMyLocationEnabled(true);
        LatLng Loc = new LatLng(Lat,Long);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Loc));
        mMap.animateCamera(zoom);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    Toast.makeText(MapsActivity.this, "Permission denied to read your Location",
                            Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MapsActivity.this,LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
                break;
            }
        }
    }

    public void Logout(View view) {
        Intent intent=new Intent(MapsActivity.this,LoginActivity.class);
        finish();
        startActivity(intent);
    }

    public void ProfileClick(View view) {
        Intent intent=new Intent(MapsActivity.this,ProfileActivity.class);
        intent.putExtra("User", CurUser.ToString());
        intent.putExtra("Quest", CurQuest.ToString());
        startActivity(intent);
    }

    public void ActionClick(View view) {
        if(CurQController.QuestIsTrue(CurQuest)) {
            CheckController GpsEnable=new CheckController();
            if(GpsEnable.GpsEnable(this)) {
                float meter = distof(CurQuest.getLat(), CurQuest.getLng());
                if (meter <= QuestOnMapRadius) {
                    CurUser = CurUController.QuestComplete(CurUser, CurQuest);
                    CurQuest = CurQController.NextQuest(CurQuest);
                    setquestonmap(CurQuest.getLat(), CurQuest.getLng());
                } else {
                    Toast.makeText(this, "You are not on the quest area", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    public void init(){
        CurQuest = new Quest();
        CurQController = new QuestController();
        CurUController = new UserController();
        Lat = 0;
        Long = 0;
        Bundle extras = getIntent().getExtras();
        String User = extras.getString("User");
        CurUser = new User();
        CurUser.setUser(User);
        TextView usernamedsp = (TextView) findViewById(R.id.username);
        usernamedsp.setText(CurUser.getUsername());
        usernamedsp.setTextColor(Color.WHITE);
        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            Lat = gps.getLatitude();
            Long = gps.getLongitude();
        }
        gps.stopUsingGPS();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    float distof(double lat,double lng){
        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
                Lat = gps.getLatitude();
                Long = gps.getLongitude();
        }
        gps.stopUsingGPS();
        Location loc1,loc2;
        loc1=new Location("");
        loc1.setLatitude(Lat);
        loc1.setLongitude(Long);
        loc2=new Location("");
        loc2.setLatitude(lat);
        loc2.setLongitude(lng);
        return loc1.distanceTo(loc2);
    }

    void setquestonmap(double lat,double lng){
        mMap.clear();
        if(CurQController.QuestIsTrue(CurQuest)){
            mMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat, lng))
                    .radius(QuestOnMapRadius)
                    .strokeColor(Color.TRANSPARENT)
                    .fillColor(0x557f7fff));
        }
    }
}
