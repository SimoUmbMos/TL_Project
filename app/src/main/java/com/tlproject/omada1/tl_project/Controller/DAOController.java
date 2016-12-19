package com.tlproject.omada1.tl_project.Controller;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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

/**
 * Created by sorar on 19/12/2016.
 */

public class DAOController implements DAOInterface {
    @Override
    public void ResetUser(String Userid) {
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(Userid + ";");
        dbref.child("lvl").setValue("1");
        dbref.child("exp").setValue("0");
        dbref.child("queston").setValue("1");
    }

    @Override
    public void EditUser(String Userid,String NewUsername) {
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(Userid + ";");
        dbref.child("username").setValue(NewUsername);
    }

    @Override
    public String LoginWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        final DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        final String[] User = new String[1];
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            User[0] =null;
                        }else{
                            final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            if(user!=null) {
                                UserRef.child(user.getUid()+";").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.getValue() == null) {
                                                    UserRef.child(user.getUid() + ";").child("lvl").setValue("1");
                                                    UserRef.child(user.getUid()+";").child("exp").setValue("0");
                                                    UserRef.child(user.getUid()+";").child("queston").setValue("1");
                                                    UserRef.child(user.getUid()+";").child("username").setValue(user.getDisplayName());
                                                    User[0] = user.getDisplayName() + ";" + user.getUid() + ";1;1;0;";

                                                }else{
                                                    String lvl = dataSnapshot.child("lvl").getValue(String.class);
                                                    String exp =dataSnapshot.child("exp").getValue(String.class);
                                                    String queston =dataSnapshot.child("queston").getValue(String.class);
                                                    String username =dataSnapshot.child("username").getValue(String.class);
                                                    User[0] = username + ";" + user.getUid() + ";" + queston + ";" + lvl + ";" + exp + ";";
                                                }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {User[0] =null;}
                                        });
                            }else{
                                User[0]=null;
                            }
                        }
                    }
                });
        return User[0];
    }

    @Override
    public String LoginWithEmail(String Email, String Password) {
        final String[] User = new String[1];
        final DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        FirebaseAuth.getInstance().signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LoginActivity", "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            User[0]=null;
                        }else{
                            final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                UserRef.child(user.getUid()+";").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String Uid = user.getUid();
                                        String queston = dataSnapshot.child("queston").getValue(String.class);
                                        String lvl=dataSnapshot.child("lvl").getValue(String.class);
                                        String exp=dataSnapshot.child("exp").getValue(String.class);
                                        String username =dataSnapshot.child("username").getValue(String.class);
                                        User[0] =username+";"+ Uid+";"+queston+";"+lvl+";"+exp;
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {User[0]=null;}
                                });
                            }else {
                                User[0] =null;
                            }
                        }
                    }
                });
        return User[0];
    }

    @Override
    public String RegistWithEmail(String Email, String Password, final String UserName) {
        final DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        final String[] User = new String[1];
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            User[0] =null;
                        }else{
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            if(user!=null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(UserName).build();
                                user.updateProfile(profileUpdates);
                                UserRef.child(user.getUid() + ";").child("lvl").setValue("1");
                                UserRef.child(user.getUid()+";").child("exp").setValue("0");
                                UserRef.child(user.getUid()+";").child("queston").setValue("1");
                                UserRef.child(user.getUid()+";").child("username").setValue(UserName);
                                User[0] =UserName+";"+user.getUid()+";"+"1;1;0;";
                            }else{
                                User[0] =null;
                            }
                        }
                    }
                });
        return User[0];
    }

    @Override
    public String getQuest(String queston) {
        DatabaseReference QuestRef = FirebaseDatabase.getInstance().getReference().child("Quest");
        final String[] Quest = new String[1];
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
                        Quest[0] = questidQuest + ";" + descQuest + ";" + expQuest + ";" + nextquestidQuest + ";" + latQuest + ";" + lngQuest + ";";
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {Quest[0] =null;}
                });
        return Quest[0];
    }

}
