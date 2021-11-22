package com.example.busaninfoapp;

import java.io.Serializable;

public class Tour implements Serializable {
    private double lat;
    private double lon;

    private String mainTitle;
    private String address;
    private String itemContents;
    private String main_img_normal;

    public Tour(double lat, double lon, String mainTitle, String address, String itemContents, String main_img_normal) {
        this.lat = lat;
        this.lon = lon;
        this.mainTitle = mainTitle;
        this.address = address;
        this.itemContents = itemContents;
        this.main_img_normal = main_img_normal;
    }
    public String getmain_img_normal() {
        return main_img_normal;
    }

    public void setLat(String main_img_normal) {
        this.main_img_normal = main_img_normal;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItemContents() {
        return itemContents;
    }

    public void setItemContents(String itemContents) {
        this.itemContents = itemContents;
    }
}
