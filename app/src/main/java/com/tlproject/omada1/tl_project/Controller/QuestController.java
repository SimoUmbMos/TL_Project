package com.tlproject.omada1.tl_project.Controller;

import android.location.Location;
import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;
import com.tlproject.omada1.tl_project.Service.DAOController;


public class QuestController implements QuestInterface {
    @Override
    public boolean QuestIsTrue(Quest CurQuest) {
        return (CurQuest.getLat()!=0 && CurQuest.getLng()!=0);
    }

    @Override
    public boolean checkAction(Quest quest, User User,
                               int QuestRadius,Location loc1,Location loc2,DAOController DAO) {
        if(loc1.distanceTo(loc2)<=QuestRadius) {
            new UserController().QuestComplete(User, quest, DAO);
            return true;
        }
        return false;
    }
}
