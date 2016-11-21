package com.tlproject.omada1.tl_project.tests;

import com.tlproject.omada1.tl_project.Model.Quest;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Created by sorar on 15/11/2016.
 */

public class TestQuestModel extends TestCase {
    @Test
    public void testQuestGetExp(){
        Quest quest=new Quest();
        int res=quest.getExp();
        assertEquals(1000,res);
    }

    @Test
    public void testQuestGetIdQuest(){
        Quest quest=new Quest();
        int res=quest.getIdquest();
        assertEquals(1,res);
    }

    @Test
    public void testQuestGetLat(){
        Quest quest=new Quest();
        double res=quest.getLat();
        assertEquals(41.075152,res);
    }

    @Test
    public void testQuestGetLng(){
        Quest quest=new Quest();
        double res=quest.getLng();
        assertEquals(23.555608,res);
    }

    @Test
    public void testQuestGetNextIdQuest(){
        Quest quest=new Quest();
        int res=quest.getNextIdQuest();
        assertEquals(2,res);
    }

    @Test
    public void testQuestGetDesc(){
        Quest quest=new Quest();
        String res=quest.getDesc();
        String ans="1o quest gia test";
        assertEquals(ans,res);
    }

    @Test
    public void testQuestToString(){
        Quest quest=new Quest();
        String res=quest.ToString();
        String ans="1;1o quest gia test;1000;2;41.075152;23.555608;";
        assertEquals(ans,res);
    }

    @Test
    public void testQuestSetQuest(){
        Quest quest=new Quest();
        quest.setQuest("1;test123;1000;2;41.075152;23.555608;");
        String res=quest.getDesc();
        String ans="test123";
        assertEquals(ans,res);
    }
}
