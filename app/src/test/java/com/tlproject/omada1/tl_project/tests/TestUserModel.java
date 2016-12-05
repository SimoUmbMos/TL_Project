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
        assertEquals(0,res);
    }

    @Test
    public void testUserExp(){
        User usr=new User();
        int res = usr.getExp();
        assertEquals(0,res);
    }

    @Test
    public void testUserGetUsername(){
        User usr=new User();
        String res = usr.getUsername();
        assertEquals(" ",res);
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
/*
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
*/
    @Test
    public void testUserSetUser(){
        User usr=new User();
        String c="simo;2;1;1;0;";
        String ans="2";
        usr.setUser(c);
        String res=usr.getUserid();
        assertEquals(ans,res);
    }
    @Test
    public void testUserQueston(){
        User usr=new User();
        usr.setQueston("1");
        String ans=usr.getQueston();
        assertEquals(ans,"1");
    }
    @Test
    public void testUserToString(){
        User usr=new User();
        String c="simo;2;1;1;0;";
        usr.setUser(c);
        String res=usr.ToString();
        String ans="simo;2;1;1;0;";
        assertEquals(ans,res);
    }
}
