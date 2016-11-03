package com.tlproject.omada1.tl_project.Model;

public class User {
    private String password,username;
    private int lvl,exp;
    public User(){
        username="test123";
        password="test123";
        lvl=2;
        exp=1000;
    }
    public String getUsername(){
        return username;
    }
    public int getLvl(){return lvl;}
    public int getExp(){return exp;}
    public void setUser(String userS){
        char c;
        String tempS="";
        int i=0;
        while ((c=userS.charAt(i)) != ';'){
            tempS=tempS+c;
            i++;
        }
        username=tempS;
        tempS="";
        i++;
        while ((c=userS.charAt(i)) != ';'){
            tempS=tempS+c;
            i++;
        }
        password=tempS;
        tempS="";
        i++;
        while ((c=userS.charAt(i)) != ';'){
            tempS=tempS+c;
            i++;
        }
        lvl=Integer.parseInt(tempS);
        tempS="";
        i++;
        while ((c=userS.charAt(i)) != ';'){
            tempS=tempS+c;
            i++;
        }
        exp=Integer.parseInt(tempS);
    }
    public boolean exist(String Username) {
        return username.equals(Username);
    }
    public boolean PasswordEq(String Password) {
        return password.equals(Password);
    }
    public String ToString(){
        return (username+";"+password+";"+String.valueOf(lvl)+";"+String.valueOf(exp)+";");
    }
}
