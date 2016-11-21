package com.tlproject.omada1.tl_project.tests;

import com.tlproject.omada1.tl_project.Model.User;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Created by sorar on 3/11/2016.
 */

public class TestUserModel extends TestCase {
    @Test
    public void testUserLvl(){
        User usr=new User();
        int res = usr.getLvl();
        assertEquals(2,res);
    }

    @Test
    public void testUserExp(){
        User usr=new User();
        int res = usr.getExp();
        assertEquals(1000,res);
    }

    @Test
    public void testUserGetUsername(){
        User usr=new User();
        String res = usr.getUsername();
        assertEquals("test123",res);
    }

    @Test
    public void testUserSetExp(){
        User usr=new User();
        usr.setExp(0);
        int res = usr.getExp();
        assertEquals(0,res);
    }

    @Test
    public void testUserSetLvl(){
        User usr=new User();
        usr.setLvl(1);
        int res = usr.getLvl();
        assertEquals(1,res);
    }

    @Test
    public void testUserExist(){
        User usr=new User();
        assertEquals(true,usr.exist("test123"));
    }

    @Test
    public void testUserPasswordEq(){
        User usr=new User();
        assertEquals(true,usr.PasswordEq("test123"));
    }

    @Test
    public void testUserSetUser(){
        User usr=new User();
        String c="simotest;123456;1;0;";
        String ans="simotest";
        usr.setUser(c);
        String res=usr.getUsername();
        assertEquals(ans,res);
    }

    @Test
    public void testUserToString(){
        User usr=new User();
        String c="simotest;123456;1;0;";
        usr.setUser(c);
        String res=usr.ToString();
        String ans="simotest;123456;1;0;";
        assertEquals(ans,res);
    }
}
