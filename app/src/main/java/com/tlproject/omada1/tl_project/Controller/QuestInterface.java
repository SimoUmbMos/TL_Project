package com.tlproject.omada1.tl_project.Controller;

import com.tlproject.omada1.tl_project.Model.Quest;

/**
 * Created by Chris on 4/11/2016.
 */

public interface QuestInterface {
    public void NextQuest(Quest CurQuest);
    public boolean QuestIsTrue(Quest CurQuest);

}
