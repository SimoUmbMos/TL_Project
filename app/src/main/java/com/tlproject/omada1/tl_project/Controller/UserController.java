package com.tlproject.omada1.tl_project.Controller;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;

public class UserController implements UserInterface {
    @Override
    public int expforLvl(User curruser) {
        int lvl=curruser.getLvl();
        return (lvl * 1000);
    }

    @Override
    public void QuestComplete(User CurUser, Quest CurQuest) {
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(CurUser.getUsername()+ ";" +CurUser.getUserid() + ";");
        int exp=CurUser.getExp();
        int expn=expforLvl(CurUser);
        exp=exp+CurQuest.getExp();
        int lvl=CurUser.getLvl();

        if(exp>=expn)
        {
            lvl++;
            exp=exp-expn;
        }
        CurUser.setExp(exp);
        CurUser.setLvl(lvl);
        CurUser.setQueston(String.valueOf(CurQuest.getNextIdQuest()));
        dbref.child("lvl").setValue(Integer.toString(lvl));
        dbref.child("exp").setValue(Integer.toString(exp));
        dbref.child("queston").setValue(String.valueOf(CurQuest.getNextIdQuest()));
        }
}

