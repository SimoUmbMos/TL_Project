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
        assertEquals(false, questController.QuestIsTrue(quest));
    }
}
