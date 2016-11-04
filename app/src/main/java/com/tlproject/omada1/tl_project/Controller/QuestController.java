package com.tlproject.omada1.tl_project.Controller;

import com.tlproject.omada1.tl_project.Model.Quest;

/**
 * Created by Chris on 4/11/2016.
 */

public class QuestController implements QuestInterface {
    @Override
    public Quest NextQuest(Quest CurQuest) {
        String Quest="2;Telos;0;3;0;0;";
        CurQuest.setQuest(Quest);
        return CurQuest;
    }

    @Override
    public boolean QuestIsTrue(Quest CurQuest) {
        return (CurQuest.getLat()!=0 && CurQuest.getLng()!=0);
    }
}
