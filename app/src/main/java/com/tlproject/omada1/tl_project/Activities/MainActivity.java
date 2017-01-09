package com.tlproject.omada1.tl_project.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlproject.omada1.tl_project.BuildConfig;
import com.tlproject.omada1.tl_project.Controller.CheckController;
import com.tlproject.omada1.tl_project.Service.DAOController;
import com.tlproject.omada1.tl_project.Model.User;
import com.tlproject.omada1.tl_project.R;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private int decision;
    private LinearLayout menuSignin;
    private RelativeLayout EmailAction;
    private Button action;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView version = (TextView) findViewById(R.id.version);
        version.setText("ver:"+BuildConfig.VERSION_NAME);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.GET_ACCOUNTS,
                            Manifest.permission.INTERNET},
                    2);
        } else {
            init();
        }
    }

    @Override
    public void onClick(View view) {
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        switch (view.getId()) {
            case R.id.SignInGoogle:
                if(new CheckController().GpsEnable(locationManager))   signInGoogle();
                else dialog();
                break;
            case R.id.SignInEmail:
                if(new CheckController().GpsEnable(locationManager))   signInEmail();
                else dialog();
                break;
            case R.id.RegistEmail:
                if(new CheckController().GpsEnable(locationManager))   RegistEmail();
                else dialog();
                break;
            case R.id.btProceed:
                if(decision==1) {
                    ProceedSignIn();
                }else if(decision==2){
                    ProceedRegist();
                }
                break;
            case R.id.BackToMenu:
                    BackToListMenu();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                firebaseAuthWithGoogle(acct);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 2: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to read your Account and internet",
                            Toast.LENGTH_SHORT).show();
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {
                                    if(status.isSuccess()) {
                                        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
                                        EditText etUsername = (EditText) findViewById(R.id.etUsername);
                                        tvUsername.setVisibility(View.VISIBLE);
                                        etUsername.setVisibility(View.VISIBLE);
                                        EmailAction.setVisibility(View.INVISIBLE);
                                        menuSignin.setVisibility(View.VISIBLE);
                                        decision=0;
                                    }
                                }
                            });
                    FirebaseAuth.getInstance().signOut();
                }
                break;
            }
        }
    }

    private void init(){
        final View.OnClickListener ThisClickListener = this;
        menuSignin = (LinearLayout) findViewById(R.id.SignInMenu);
        EmailAction = (RelativeLayout) findViewById(R.id.EmailAction);
        action = (Button) findViewById(R.id.btProceed);
        menuSignin.setVisibility(View.INVISIBLE);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    LoginFB(user);
                    Log.d("LoginActivity", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    menuSignin.setVisibility(View.VISIBLE);
                    decision = 0;
                    findViewById(R.id.SignInGoogle).setOnClickListener(ThisClickListener);
                    findViewById(R.id.SignInEmail).setOnClickListener(ThisClickListener);
                    findViewById(R.id.RegistEmail).setOnClickListener(ThisClickListener);
                    findViewById(R.id.btProceed).setOnClickListener(ThisClickListener);
                    findViewById(R.id.BackToMenu).setOnClickListener(ThisClickListener);

                }
            }
        };
    }

    private void  signInEmail() {
        decision=1;
        menuSignin.setVisibility(View.GONE);
        EmailAction.setVisibility(View.VISIBLE);
        TextView tvUsername=(TextView) findViewById(R.id.tvUsername);
        EditText etUsername=(EditText) findViewById(R.id.etUsername);
        tvUsername.setVisibility(View.GONE);
        etUsername.setVisibility(View.GONE);
        action.setText("Sign In");
    }

    private void  RegistEmail() {
        decision=2;
        menuSignin.setVisibility(View.GONE);
        EmailAction.setVisibility(View.VISIBLE);
        action.setText("Regist");
    }

    private void  BackToListMenu() {
        TextView tvUsername=(TextView) findViewById(R.id.tvUsername);
        EditText etUsername=(EditText) findViewById(R.id.etUsername);
        tvUsername.setVisibility(View.VISIBLE);
        etUsername.setVisibility(View.VISIBLE);
        EmailAction.setVisibility(View.INVISIBLE);
        menuSignin.setVisibility(View.VISIBLE);
    }

    private void  ProceedSignIn() {
        EditText emailET=(EditText) findViewById(R.id.etEmail);
        EditText passwordET=(EditText) findViewById(R.id.etPassword);
        EditText usernameET=(EditText) findViewById(R.id.etUsername);
        String email = emailET.getText().toString();
        String pass = passwordET.getText().toString();
        EmailAction.setVisibility(View.INVISIBLE);
        menuSignin.setVisibility(View.INVISIBLE);
        DAOController DAO=new DAOController();
        String User= DAO.LoginWithEmail(email,pass);
        if(User!=null){
            User user=new User();
            user.setUser(User);
            String Quest =DAO.getQuest(user.getQueston());
            if(Quest!=null){
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("User", User);
                intent.putExtra("Quest", Quest);
                startActivity(intent);
            }
        }
        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvUsername.setVisibility(View.VISIBLE);
        usernameET.setVisibility(View.VISIBLE);
        EmailAction.setVisibility(View.INVISIBLE);
        menuSignin.setVisibility(View.VISIBLE);
        decision = 0;
    }

    private void  ProceedRegist() {
        EmailAction.setVisibility(View.INVISIBLE);
        menuSignin.setVisibility(View.INVISIBLE);
        EditText emailET=(EditText) findViewById(R.id.etEmail);
        EditText passwordET=(EditText) findViewById(R.id.etPassword);
        EditText usernameET=(EditText) findViewById(R.id.etUsername);
        DAOController DAO=new DAOController();
        String User= DAO.RegistWithEmail(emailET.getText().toString(),passwordET.getText().toString(),usernameET.getText().toString());
        if(User!=null){
            User user=new User();
            user.setUser(User);
            String Quest =DAO.getQuest(user.getQueston());
            if(Quest!=null){
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("User", User);
                intent.putExtra("Quest", Quest);
                startActivity(intent);
            }
        }
        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvUsername.setVisibility(View.VISIBLE);
        usernameET.setVisibility(View.VISIBLE);
        EmailAction.setVisibility(View.INVISIBLE);
        menuSignin.setVisibility(View.VISIBLE);
        decision = 0;
    }

    private void  signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        EmailAction.setVisibility(View.INVISIBLE);
        menuSignin.setVisibility(View.INVISIBLE);
        DAOController DAO=new DAOController();
        String User= DAO.LoginWithGoogle(acct);
        if(User!=null){
            User user=new User();
            user.setUser(User);
            String Quest =DAO.getQuest(user.getQueston());
            if(Quest!=null){
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("User", User);
                intent.putExtra("Quest", Quest);
                startActivity(intent);
            }
        }
        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        tvUsername.setVisibility(View.VISIBLE);
        etUsername.setVisibility(View.VISIBLE);
        EmailAction.setVisibility(View.INVISIBLE);
        menuSignin.setVisibility(View.VISIBLE);
        decision = 0;
    }

    private void LoginFB(final FirebaseUser user) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid() + ";")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String lvl = dataSnapshot.child("lvl").getValue(String.class);
                        final String exp = dataSnapshot.child("exp").getValue(String.class);
                        final String queston = dataSnapshot.child("queston").getValue(String.class);
                        final String username =dataSnapshot.child("username").getValue(String.class);
                        if (username!=null && lvl != null && exp != null && queston != null) {
                            TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
                            EditText etUsername = (EditText) findViewById(R.id.etUsername);
                            tvUsername.setVisibility(View.VISIBLE);
                            etUsername.setVisibility(View.VISIBLE);
                            EmailAction.setVisibility(View.INVISIBLE);
                            menuSignin.setVisibility(View.VISIBLE);
                            decision = 0;
                            FirebaseDatabase.getInstance().getReference().child("Quest").child("Quest" + queston)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String descQuest = dataSnapshot.child("desc").getValue(String.class);
                                            String expQuest = dataSnapshot.child("exp").getValue(String.class);
                                            String latQuest = dataSnapshot.child("lat").getValue(String.class);
                                            String lngQuest = dataSnapshot.child("long").getValue(String.class);
                                            String nextquestidQuest = dataSnapshot.child("nextquestid").getValue(String.class);
                                            String questidQuest = dataSnapshot.child("questid").getValue(String.class);
                                            String Quest = questidQuest + ";" + descQuest + ";" + expQuest + ";" + nextquestidQuest + ";" + latQuest + ";" + lngQuest + ";";
                                            String User = username + ";" + user.getUid() + ";" + queston + ";" + lvl + ";" + exp + ";";
                                            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.putExtra("User", User);
                                            intent.putExtra("Quest", Quest);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                        } else {
                            FirebaseAuth.getInstance().signOut();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
    private void dialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Gps Not Enable");
        Button ButtonYes = (Button) dialog.findViewById(R.id.btn_yes);
        Button ButtonNo = (Button) dialog.findViewById(R.id.btn_no);
        ButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        ButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
