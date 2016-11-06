package com.tlproject.omada1.tl_project;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private Quest CurQuest;
    private double Lat, Long;
    private User CurUser;
    private GoogleMap mMap;
    private UserController CurUController;
    private QuestController CurQController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        CurQuest=new Quest();
        CurQController=new QuestController();
        CurUController=new UserController();
        Lat = 0;
        Long = 0;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
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
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setquestonmap(CurQuest.getLat(),CurQuest.getLng());
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
        intent.putExtra("User", CurUser.ToString());
        intent.putExtra("Quest", CurQuest.ToString());
        startActivity(intent);
    }

    void setquestonmap(double lat,double lng){
        mMap.clear();
        if(CurQController.QuestIsTrue(CurQuest)){
             mMap.addCircle(new CircleOptions().center(new LatLng(lat, lng)).radius(60).strokeColor(Color.TRANSPARENT).fillColor(0x557f7fff));
        }
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

    public void ActionClick(View view) {
        if(CurQController.QuestIsTrue(CurQuest)) {
            CheckController GpsEnable=new CheckController();
            if(GpsEnable.GpsEnable(this)) {
                GPSTracker gps = new GPSTracker(this);
                if (gps.canGetLocation()) {
                    Lat = gps.getLatitude();
                    Long = gps.getLongitude();
                }
                gps.stopUsingGPS();
                float meter = distof(Lat, Long, CurQuest.getLat(), CurQuest.getLng());
                if (meter <= 60) {
                    CurUser = CurUController.QuestComplete(CurUser, CurQuest);
                    CurQuest = CurQController.NextQuest(CurQuest);
                    setquestonmap(CurQuest.getLat(), CurQuest.getLng());
                } else {
                    Toast.makeText(this, "You are not on the quest area", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
