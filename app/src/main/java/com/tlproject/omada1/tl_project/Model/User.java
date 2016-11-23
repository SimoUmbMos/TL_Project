package com.tlproject.omada1.tl_project.Model;

public class User {

    private String name;
    private int queston;
    private int lvl;
    private int exp;

    public User() {
    }

    public User(String Name) {
        this.name = Name;
        this.queston = 1;
        this.lvl = 1;
        this.exp = 500;
    }

    public User(String Name, int Queston, int Lvl, int Exp){
        this.name=Name;
        this.queston=Queston;
        this.lvl=Lvl;
        this.exp=Exp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQueston() {
        return queston;
    }

    public void setQueston(int queston) {
        this.queston = queston;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}