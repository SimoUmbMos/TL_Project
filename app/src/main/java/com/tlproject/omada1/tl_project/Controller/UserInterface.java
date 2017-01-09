package com.tlproject.omada1.tl_project.Controller;

import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;
import com.tlproject.omada1.tl_project.Service.DAOController;

/**
 * Created by sorar on 2/11/2016.
 */

public interface UserInterface {
    int expforLvl(User curruser);
    boolean QuestComplete(User CurUser, Quest CurQuest, DAOController DAO);
}
