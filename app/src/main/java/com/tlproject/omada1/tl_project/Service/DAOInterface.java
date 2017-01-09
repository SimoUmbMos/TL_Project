package com.tlproject.omada1.tl_project.Service;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;

public interface DAOInterface {
    boolean ResetUser (User CurUser);
    boolean EditUser (User CurUser, String NewUsername);
    boolean save(final Quest CurQuest, User CurUser);
    String LoginWithGoogle(GoogleSignInAccount acct);
    String LoginWithEmail(String Email,String Password);
    String RegistWithEmail(String Email,String Password,String Username);
    String getQuest(String queston);
}
