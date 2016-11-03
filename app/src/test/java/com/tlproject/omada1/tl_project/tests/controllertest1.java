package com.tlproject.omada1.tl_project.tests;

import com.tlproject.omada1.tl_project.Model.User;

import junit.framework.TestCase;

/**
 * Created by sorar on 3/11/2016.
 */

public class controllertest1 extends TestCase {
    public void testUserLvl(){
        User usr=new User();
        int res = usr.getLvl();
        assertEquals(2,res);
    }
    public void testUserExp(){
        User usr=new User();
        int res = usr.getExp();
        assertEquals(1000,res);
    }

}
