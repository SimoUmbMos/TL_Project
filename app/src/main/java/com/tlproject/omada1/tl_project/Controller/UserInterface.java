package com.tlproject.omada1.tl_project.Controller;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;

/**
 * Created by sorar on 2/11/2016.
 */

public interface UserInterface {
    public int expforLvl(User curruser);
    public User QuestComplete(User CurUser,Quest CurQuest);
}
