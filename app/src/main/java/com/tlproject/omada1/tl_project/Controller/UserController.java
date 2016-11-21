package com.tlproject.omada1.tl_project.Controller;

import android.content.Context;
import android.widget.Toast;

import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;

public class UserController implements UserInterface {
    @Override
    public boolean Login(String Username, String Password, Context context) {
        User User=new User();

        if(User.exist(Username)){
            if(User.PasswordEq(Password)){
                return true;
            }else{
              Toast.makeText(context, "incorrect Password", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
           Toast.makeText(context, "Username don't exist", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public boolean Regist(String Username, String Password, String cPassword, Context context) {
        if(Username.length()>5 && Username.length()<13){
            if(Password.length()>5 && Password.length()<17){
                if(Password.equals(cPassword)){
                    return true;
                }else{
                    Toast.makeText(context, "Password doesn't match", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{
                Toast.makeText(context, "Password must be from 6 to 16 char", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(context, "Username must be from 6 to 12 char", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public int expforLvl(User curruser) {
        int lvl=curruser.getLvl();
        return (lvl * 1000);
    }

    @Override
    public User QuestComplete(User CurUser, Quest CurQuest) {
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
        return  CurUser;
        }
}

