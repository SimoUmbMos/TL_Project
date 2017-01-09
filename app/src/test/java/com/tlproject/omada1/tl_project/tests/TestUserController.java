package com.tlproject.omada1.tl_project.tests;


import android.location.Location;

import com.tlproject.omada1.tl_project.Service.DAOController;
import com.tlproject.omada1.tl_project.Controller.UserController;
import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;


public class TestUserController {
    @Test
    public void testUserConexpforLvl(){
        UserController userController=new UserController();
        User user=new User();
        user.setLvl(1);
        int res=userController.expforLvl(user);
        assertEquals(1000, res);
    }
    @Test
    public void testQuestComplete(){
        UserController userController=new UserController();
        User user=new User();
        Quest quest=new Quest();
        DAOController DAO= Mockito.mock(DAOController.class);
        when(DAO.save(quest,user)).thenReturn(true);
        assertTrue(userController.QuestComplete(user,quest,DAO));
    }
}
