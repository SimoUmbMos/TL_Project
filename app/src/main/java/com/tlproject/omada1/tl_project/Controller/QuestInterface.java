package com.tlproject.omada1.tl_project.Controller;

import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;

/**
 * Created by Chris on 4/11/2016.
 */

public interface QuestInterface {
    void NextQuest(Quest CurQuest);
    boolean QuestIsTrue(Quest CurQuest);
    boolean checkAction(Quest quest, User User, int QuestRadius, Double Lat, Double Long);
    public boolean checkQuestMark(Quest quest,int QuestRadius,Double Lat,Double Long);
}
