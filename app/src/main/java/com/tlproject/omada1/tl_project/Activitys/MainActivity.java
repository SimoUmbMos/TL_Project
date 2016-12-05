package com.tlproject.omada1.tl_project.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlproject.omada1.tl_project.Controller.CheckController;
import com.tlproject.omada1.tl_project.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private int decision;
    private LinearLayout menuSignin;
    private RelativeLayout EmailAction;
    //private TextView Useretv, UserUIDtv,UserLvltv,UserExptv,UserQuestOn;
    private Button action;//,logout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference dbref= FirebaseDatabase.getInstance().getReference();
    private DatabaseReference UserRef = dbref.child("Users");
    private DatabaseReference QuestRef = dbref.child("Quest");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        switch (view.getId()) {
            case R.id.SignInGoogle:
                if(new CheckController().GpsEnable(this))
                signInGoogle();
                break;
            case R.id.SignInEmail:
                if(new CheckController().GpsEnable(this))
                signInEmail();
                break;
            case R.id.RegistEmail:
                if(new CheckController().GpsEnable(this))
                RegistEmail();
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
            /*case R.id.LogOut:
                signOut();
                break;*/
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
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

    private void init(){
            final View.OnClickListener ThisClickListener = this;
            menuSignin = (LinearLayout) findViewById(R.id.SignInMenu);
            EmailAction = (RelativeLayout) findViewById(R.id.EmailAction);
            /*Useretv = (TextView) findViewById(R.id.UsernameDsp);
            UserUIDtv = (TextView) findViewById(R.id.Useruid);
            UserLvltv = (TextView) findViewById(R.id.Userlvl);
            UserExptv = (TextView) findViewById(R.id.Userexp);
            UserQuestOn = (TextView) findViewById(R.id.Userqueston);*/
            action = (Button) findViewById(R.id.btProceed);
            //logout = (Button) findViewById(R.id.LogOut);

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
                        UserRef.child(user.getDisplayName() + ";" + user.getUid() + ";")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String lvl = dataSnapshot.child("lvl").getValue(String.class);
                                        String exp = dataSnapshot.child("exp").getValue(String.class);
                                        String queston = dataSnapshot.child("queston").getValue(String.class);
                                        if (lvl != null && exp != null && queston != null) {
                                            login(user.getDisplayName(), user.getUid(), queston, lvl, exp);
                                        } else {
                                            FirebaseAuth.getInstance().signOut();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                        Log.d("LoginActivity", "onAuthStateChanged:signed_in:" + user.getUid());
                    } else {
                        menuSignin.setVisibility(View.VISIBLE);
                        decision = 0;
                        findViewById(R.id.SignInGoogle).setOnClickListener(ThisClickListener);
                        findViewById(R.id.SignInEmail).setOnClickListener(ThisClickListener);
                        findViewById(R.id.RegistEmail).setOnClickListener(ThisClickListener);
                        findViewById(R.id.btProceed).setOnClickListener(ThisClickListener);
                        findViewById(R.id.BackToMenu).setOnClickListener(ThisClickListener);
                        //findViewById(R.id.LogOut).setOnClickListener(ThisClickListener);
                        Log.d("LoginActivity", "onAuthStateChanged:signed_out");
                    }
                }
            };
    }

    private void  signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
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

    private void  ProceedSignIn() {
        EditText emailET=(EditText) findViewById(R.id.etEmail);
        EditText passwordET=(EditText) findViewById(R.id.etPassword);
        mAuth.signInWithEmailAndPassword(emailET.getText().toString(),passwordET.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LoginActivity", "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w("LoginActivity", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Fail:Email or Password Wrong",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            final FirebaseUser user=mAuth.getCurrentUser();
                            if (user != null) {
                                EmailAction.setVisibility(View.INVISIBLE);
                                menuSignin.setVisibility(View.INVISIBLE);
                                UserRef.child(user.getDisplayName()+";"+user.getUid()+";").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String Uid = user.getUid();
                                        String queston = dataSnapshot.child("queston").getValue(String.class);
                                        String lvl=dataSnapshot.child("lvl").getValue(String.class);
                                        String exp=dataSnapshot.child("exp").getValue(String.class);
                                            login(user.getDisplayName(), Uid,queston,lvl,exp);

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {}
                                });
                            }
                        }
                    }
                });
    }

    private void  ProceedRegist() {
        EditText emailET=(EditText) findViewById(R.id.etEmail);
        EditText passwordET=(EditText) findViewById(R.id.etPassword);
        mAuth.createUserWithEmailAndPassword(emailET.getText().toString(),passwordET.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LoginActivity", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Fail:Email Exist",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            FirebaseUser user=mAuth.getCurrentUser();
                            if(user!=null) {
                                EmailAction.setVisibility(View.INVISIBLE);
                                menuSignin.setVisibility(View.INVISIBLE);
                                EditText usernameET=(EditText) findViewById(R.id.etUsername);
                                String UserName = usernameET.getText().toString();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(UserName).build();
                                user.updateProfile(profileUpdates);
                                UserRef.child(UserName + ";" + user.getUid() + ";").child("lvl").setValue("1");
                                UserRef.child(UserName+";"+user.getUid()+";").child("exp").setValue("0");
                                UserRef.child(UserName+";"+user.getUid()+";").child("queston").setValue("1");
                                    login(UserName,user.getUid(),"1","1","0");
                            }
                        }
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("LoginActivity", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            firebaseAuthWithGoogle(acct);
        } /*else {
            //Cant Login
        }*/
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LoginActivity", "signInWithCredential:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w("LoginActivity", "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            final FirebaseUser user=mAuth.getCurrentUser();
                            if(user!=null) {
                                EmailAction.setVisibility(View.INVISIBLE);
                                menuSignin.setVisibility(View.INVISIBLE);
                                UserRef.child(user.getDisplayName()+";"+user.getUid()+";")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getValue() == null) {
                                            UserRef.child(user.getDisplayName() + ";" + user.getUid() + ";").child("lvl").setValue("1");
                                            UserRef.child(user.getDisplayName()+";"+user.getUid()+";").child("exp").setValue("0");
                                            UserRef.child(user.getDisplayName()+";"+user.getUid()+";").child("queston").setValue("1");
                                            String lvl = dataSnapshot.child("lvl").getValue(String.class);
                                            String exp =dataSnapshot.child("exp").getValue(String.class);
                                            String queston =dataSnapshot.child("queston").getValue(String.class);
                                            login(user.getDisplayName(),user.getUid(),lvl,exp,queston);
                                        }else{
                                            String lvl = dataSnapshot.child("lvl").getValue(String.class);
                                            String exp =dataSnapshot.child("exp").getValue(String.class);
                                            String queston =dataSnapshot.child("queston").getValue(String.class);
                                            login(user.getDisplayName(),user.getUid(),queston,lvl, exp);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {}
                                });
                            }
                        }
                    }
                });
    }

    private void login(final String displayName, final String uid, final String queston, final String lvl, final String exp) {
        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        tvUsername.setVisibility(View.VISIBLE);
        etUsername.setVisibility(View.VISIBLE);
        EmailAction.setVisibility(View.INVISIBLE);
        menuSignin.setVisibility(View.VISIBLE);
        /*Useretv.setVisibility(View.INVISIBLE);
        UserLvltv.setVisibility(View.INVISIBLE);
        UserExptv.setVisibility(View.INVISIBLE);
        UserUIDtv.setVisibility(View.INVISIBLE);
        UserQuestOn.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);*/
        decision = 0;
        QuestRef.child("Quest" + queston)
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
                        String User = displayName + ";" + uid + ";" + queston + ";" + lvl + ";" + exp + ";";
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
    }

    private void  BackToListMenu() {
        TextView tvUsername=(TextView) findViewById(R.id.tvUsername);
        EditText etUsername=(EditText) findViewById(R.id.etUsername);
        tvUsername.setVisibility(View.VISIBLE);
        etUsername.setVisibility(View.VISIBLE);
        EmailAction.setVisibility(View.INVISIBLE);
        menuSignin.setVisibility(View.VISIBLE);
    }

    private void signOut() {
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
                            /*Useretv.setVisibility(View.INVISIBLE);
                            UserLvltv.setVisibility(View.INVISIBLE);
                            UserExptv.setVisibility(View.INVISIBLE);
                            UserUIDtv.setVisibility(View.INVISIBLE);
                            UserQuestOn.setVisibility(View.INVISIBLE);
                            logout.setVisibility(View.INVISIBLE);*/
                            decision=0;
                        }
                    }
                });
        FirebaseAuth.getInstance().signOut();
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
                    signOut();
                }
                break;
            }
        }
    }
}
