package com.tlproject.omada1.tl_project.Model;

/**
 * Created by Chris on 4/11/2016.
 */

public class Quest {
    private String desc;
    private int exp;
    private double lat,lng;

    public Quest() {
    }

    public Quest(String ddesc,int eexp,double llat,double llng){
        this.desc=ddesc;
        this.exp=eexp;
        this.lat=llat;
        this.lng=llng;
    }

    public int getExp() {
        return exp;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getDesc() {
        return desc;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
