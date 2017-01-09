package com.tlproject.omada1.tl_project.tests;

import android.location.Location;

import com.tlproject.omada1.tl_project.Controller.QuestController;
import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;
import com.tlproject.omada1.tl_project.Service.DAOController;

import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TestQuestController {
    @Test
    public void testQuestConQuestIsFalse(){
        Quest quest=new Quest();
        QuestController questController=new QuestController();
        assertEquals(false, questController.QuestIsTrue(quest));
    }
    @Test
    public void testcheckActionTrue(){
        Quest quest=new Quest();
        QuestController questController=new QuestController();
        User user=new User();
        DAOController DAO= Mockito.mock(DAOController.class);
        when(DAO.save(quest,user)).thenReturn(true);
        Location loc1=Mockito.mock(Location.class);
        Location loc2=Mockito.mock(Location.class);
        when(loc1.distanceTo(loc2)).thenReturn((float) 1);
        assertTrue(questController.checkAction(quest,user,60,loc1,loc2,DAO));
    }
    @Test
    public void testcheckActionFalse(){
        Quest quest=new Quest();
        QuestController questController=new QuestController();
        User user=new User();
        DAOController DAO= Mockito.mock(DAOController.class);
        when(DAO.save(quest,user)).thenReturn(true);
        Location loc1=Mockito.mock(Location.class);
        Location loc2=Mockito.mock(Location.class);
        when(loc1.distanceTo(loc2)).thenReturn((float) 61);
        assertFalse(questController.checkAction(quest,user,60,loc1,loc2,DAO));
    }
}
