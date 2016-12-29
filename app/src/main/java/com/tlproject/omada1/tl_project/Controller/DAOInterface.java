package com.tlproject.omada1.tl_project.Controller;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;

public interface DAOInterface {
    void ResetUser (DatabaseReference dbref);
    void EditUser (DatabaseReference dbref,String NewUsername);
    String LoginWithGoogle(GoogleSignInAccount acct);
    String LoginWithEmail(String Email,String Password);
    String RegistWithEmail(String Email,String Password,String Username);
    String getQuest(String queston);
}
