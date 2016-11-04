package com.tlproject.omada1.tl_project.Model;

public class User {
    /**
     * String with the users password
     */
    private String password;
    /**
     * String with the users username
     */
    private String username;
    /**
     * Int with the users Level
     */
    private int lvl;
    /**
     * Int with the users experience point
     */
    private int exp;
    /**
     *
     */
    public User(){
        username="test123";
        password="test123";
        lvl=2;
        exp=1000;
    }
    /**
     * Return the users username
     */
    public String getUsername(){
        return username;
    }
    /**
     * Return the users Level
     */
    public int getLvl(){return lvl;}
    /**
     * Return the users experience point
     */
    public int getExp(){return exp;}
    /**
     * Set user data from a string with a format like User.toString();
     */
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
    /**
     * Compare the given username with the user username
     */
    public boolean exist(String Username) {
        return username.equals(Username);
    }
    /**
     *  Compare the given password with the user password
     */
    public boolean PasswordEq(String Password) {
        return password.equals(Password);
    }
    /**
     * Return User data as String
     */
    public String ToString(){
        return (username+";"+password+";"+String.valueOf(lvl)+";"+String.valueOf(exp)+";");
    }
}
