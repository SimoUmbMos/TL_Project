package com.tlproject.omada1.tl_project.Controller;

import android.location.Location;

import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;
import com.tlproject.omada1.tl_project.Service.DAOController;

public interface QuestInterface {
    boolean QuestIsTrue(Quest CurQuest);
    boolean checkAction(Quest quest, User User, int QuestRadius, Location loc1, Location loc2, DAOController DAO);
}
