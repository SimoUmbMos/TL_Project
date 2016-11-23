package com.tlproject.omada1.tl_project.Controller;

import com.tlproject.omada1.tl_project.Model.Quest;

/**
 * Created by Chris on 4/11/2016.
 */

public class QuestController implements QuestInterface {
   /* @Override
    public Quest NextQuest(Quest CurQuest) {
        int nextid=CurQuest.getNextIdQuest();
        String Quest;
        switch (nextid) {
            case 2:
                Quest = "2;2o Quest;1000;3;41.074600;23.555120;";
                CurQuest.setQuest(Quest);
                break;
            case 3:
                Quest = "3;Telos;0;4;0;0;";
                CurQuest.setQuest(Quest);
                break;
        }
        return CurQuest;
    }*/

    @Override
    public boolean QuestIsTrue(Quest CurQuest) {
        return (CurQuest.getLat()!=0 && CurQuest.getLng()!=0);
    }
}
