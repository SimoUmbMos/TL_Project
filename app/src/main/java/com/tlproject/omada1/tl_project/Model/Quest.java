package com.tlproject.omada1.tl_project.Model;

/**
 * Created by Chris on 4/11/2016.
 */

public class Quest {
    private String desc;
    private int idquest,exp,nextIdQuest;
    private double lat,lng;
    public Quest() {
        desc = "1o quest gia test";
        idquest = 1;
        exp = 1000;
        nextIdQuest = 2;
        lat = 41.075152;
        lng = 23.555608;
    }
    public int getExp() {
        return exp;
    }

    public int getIdquest() {
        return idquest;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getNextIdQuest() {
        return nextIdQuest;
    }

    public String getDesc() {
        return desc;
    }
    public String ToString(){
        return (String.valueOf(idquest)+";"+desc+";"+String.valueOf(exp)+";"+String.valueOf(nextIdQuest)+";"+String.valueOf(lat)+";"+String.valueOf(lng)+";");
    }
    public void setQuest(String Quests){
        char c;
        String tempS="";
        int i=0;
        while ((c=Quests.charAt(i)) != ';'){
            tempS=tempS+c;
            i++;
        }
        idquest=Integer.parseInt(tempS);
        tempS="";
        i++;
        while ((c=Quests.charAt(i)) != ';'){
            tempS=tempS+c;
            i++;
        }
        desc=tempS;
        tempS="";
        i++;
        while ((c=Quests.charAt(i)) != ';'){
            tempS=tempS+c;
            i++;
        }
        exp=Integer.parseInt(tempS);
        tempS="";
        i++;
        while ((c=Quests.charAt(i)) != ';'){
            tempS=tempS+c;
            i++;
        }
        nextIdQuest=Integer.parseInt(tempS);
        tempS="";
        i++;
        while ((c=Quests.charAt(i)) != ';'){
            tempS=tempS+c;
            i++;
        }
        lat= Double.parseDouble(tempS);
        tempS="";
        i++;
        while ((c=Quests.charAt(i)) != ';'){
            tempS=tempS+c;
            i++;
        }
        lng=Double.parseDouble(tempS);
    }
}
