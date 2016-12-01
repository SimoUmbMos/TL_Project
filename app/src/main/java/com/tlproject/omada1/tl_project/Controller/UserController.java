package com.tlproject.omada1.tl_project.Controller;

import android.content.Context;
import android.widget.Toast;

import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;

public class UserController implements UserInterface {
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

