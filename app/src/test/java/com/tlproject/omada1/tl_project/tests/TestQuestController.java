package com.tlproject.omada1.tl_project.tests;

import com.tlproject.omada1.tl_project.Controller.QuestController;
import com.tlproject.omada1.tl_project.Model.Quest;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by sorar on 15/11/2016.
 */

public class TestQuestController {
    @Test
    public void testQuestConQuestIsTrue(){
        Quest quest=new Quest();
        QuestController questController=new QuestController();
        assertEquals(true, questController.QuestIsTrue(quest));
    }

    @Test
    public void testQuestConNextQuest1(){
        Quest quest=new Quest();
        QuestController questController=new QuestController();
        quest=questController.NextQuest(quest);
        assertEquals(2, quest.getIdquest());
    }

    @Test
    public void testQuestConNextQuest2(){
        Quest quest=new Quest();
        quest.setQuest("2;2o Quest;1000;3;41.074600;23.555120;");
        QuestController questController=new QuestController();
        quest=questController.NextQuest(quest);
        assertEquals(3, quest.getIdquest());
    }
}
