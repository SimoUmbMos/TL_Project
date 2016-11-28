package com.tlproject.omada1.tl_project.Activities;

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

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlproject.omada1.tl_project.Controller.CheckController;
import com.tlproject.omada1.tl_project.Controller.QuestController;
import com.tlproject.omada1.tl_project.Controller.UserController;
import com.tlproject.omada1.tl_project.GPSTrack.GPSTracker;
import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;
import com.tlproject.omada1.tl_project.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private UserController CurUController;
    private QuestController CurQController;
    private User CurUser;
    private Quest CurQuest;
    //Map
    private int QuestOnMapRadius = 60;
    private GoogleMap mMap;
    private double Lat=0, Lng=0;
    //Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mQuestDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN = 1;
    //User
        public String Name, uid;
        public int Lvl, uExp,Queston;
    //Quest
        public String Desc;
        public int qExp;
        private double qLat=0, qLng=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //init Firebase components
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mUsersDatabaseReference=mFirebaseDatabase.getReference().child("Users");
        mQuestDatabaseReference = mFirebaseDatabase.getReference().child("Quests");

        //Bottom of onCreate for Firebase
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fuser = firebaseAuth.getCurrentUser();

                if (fuser != null) {
                    // User is signed in
                    User user = new User(fuser.getDisplayName());
                    uid =fuser.getUid();
                    mUsersDatabaseReference.child(uid).setValue(user);
                    fsetUser(mUsersDatabaseReference.child(uid));
                    fsetQuest(mQuestDatabaseReference.child("Quest3"));
                    TextView usernamedsp = (TextView) findViewById(R.id.username);
                    usernamedsp.setText(fuser.getDisplayName());
                    usernamedsp.setTextColor(Color.WHITE);

                    if(CurUser!=null)init();
                } else {
                    // User is signed out
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(
                                            AuthUI.EMAIL_PROVIDER,
                                            AuthUI.GOOGLE_PROVIDER)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AuthUI.getInstance().signOut(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       // setquestonmap(CurQuest.getLat(), CurQuest.getLng());
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        mMap.setMyLocationEnabled(true);
        LatLng Loc = new LatLng(Lat, Lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Loc));
        mMap.animateCamera(zoom);
    }

    public void init(){
        //String cQuest="Quest"+String.valueOf(CurUser.getQueston());
        //fsetQuest(mQuestDatabaseReference.child(cQuest));
        CurQController = new QuestController();
        CurUController = new UserController();

        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            Lat = gps.getLatitude();
            Lng = gps.getLongitude();
        }
        gps.stopUsingGPS();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setquestonmap(CurQuest.getLat(),CurQuest.getLng());
    }

    public void fsetUser(DatabaseReference ref){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                CurUser=user;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void fsetQuest(DatabaseReference ref){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Quest quest = dataSnapshot.getValue(Quest.class);
                CurQuest=quest;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void ProfileClick(View view) {
        Intent intent=new Intent(MapsActivity.this,ProfileActivity.class);
        intent.putExtra("NAME",CurUser.getName());
        intent.putExtra("EXP", CurUser.getExp());
        intent.putExtra("LVL",CurUser.getLvl());
        intent.putExtra("QUESTON",CurUser.getQueston());
        intent.putExtra("QUESTD",CurQuest.getDesc());
        startActivity(intent);

    }    //TODO: Bug with saving user's data

    public void Logout(View view) {
        AuthUI.getInstance().signOut(this);
    }

    public void setquestonmap(double lat,double lng){
        if(mMap!=null){ mMap.clear();
        if(CurQController.QuestIsTrue(CurQuest)){
            mMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat, lng))
                    .radius(QuestOnMapRadius)
                    .strokeColor(Color.TRANSPARENT)
                    .fillColor(0x557f7fff));
        }
    }}

    public void ActionClick(View view) {
        if(CurQController.QuestIsTrue(CurQuest)) {
            CheckController GpsEnable=new CheckController();
            if(GpsEnable.GpsEnable(this)) {
                float meter = distof(CurQuest.getLat(), CurQuest.getLng());
                if (meter <= QuestOnMapRadius) {
                    CurUser = CurUController.QuestComplete(CurUser, CurQuest);

                    fsetQuest(mQuestDatabaseReference.child("Quest2"));
                    setquestonmap(CurQuest.getLat(), CurQuest.getLng());
                    //CurQuest = CurQController.NextQuest(CurUser,CurQuest);//TODO: next quest
                    //setquestonmap(CurQuest.getLat(), CurQuest.getLng());
                } else {
                    Toast.makeText(this, "You are not on the quest area", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    public float distof(double lat,double lng){
        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
                Lat = gps.getLatitude();
                Lng = gps.getLongitude();
        }
        gps.stopUsingGPS();
        Location loc1,loc2;
        loc1=new Location("");
        loc1.setLatitude(Lat);
        loc1.setLongitude(Lng);
        loc2=new Location("");
        loc2.setLatitude(lat);
        loc2.setLongitude(lng);
        return loc1.distanceTo(loc2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //init(CurUser,CurQuest);
                } else {
                    Toast.makeText(MapsActivity.this, "Permission denied to read your Location",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            }
        }
    }

}
