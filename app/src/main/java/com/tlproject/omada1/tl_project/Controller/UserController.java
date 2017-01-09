package com.tlproject.omada1.tl_project.Controller;


import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;
import com.tlproject.omada1.tl_project.Service.DAOController;

public class UserController implements UserInterface {
    @Override
    public int expforLvl(User curruser) {
        int lvl=curruser.getLvl();
        return (lvl * 1000);
    }

    @Override
    public boolean QuestComplete(User CurUser, Quest CurQuest, DAOController DAO) {
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
        return(DAO.save(CurQuest,CurUser));
    }
}

