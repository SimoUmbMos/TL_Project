package com.tlproject.omada1.tl_project.Model;

public class User {
    /**
     * String with the users password
     */
    private String userid;
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
    private String queston;
    public User(){
        username=" ";
        userid=" ";
        lvl=0;
        exp=0;
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
     * Set user Level
     */
    public void setLvl(int lvl1){lvl=lvl1;}
    /**
     * Set user experience point
     */
    public void setExp(int exp1){exp=exp1;}
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
        userid=tempS;
        tempS="";
        i++;
        while ((c=userS.charAt(i)) != ';'){
            tempS=tempS+c;
            i++;
        }
        queston=tempS;
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
    public String ToString(){
        return (username+";"+userid+";"+queston+";"+String.valueOf(lvl)+";"+String.valueOf(exp)+";");
    }
    
}
