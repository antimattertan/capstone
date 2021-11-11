package com.example.busaninfoapp;

public class MyRestaurant {
    String title;
    String addr;
    String telNum;
    String menu;
    String businessHours;
    String explanation;
    String imageurl;
    double lat;
    double lng;
    int SEQ;

    public MyRestaurant(int SEQ, String title, String addr, String telNum, String menu, String businessHours
            , String explanation, double lat, double lng, String imageurl) {
        this.SEQ = SEQ;
        this.title = title;
        this.addr = addr;
        this.telNum = telNum;
        this.menu = menu;
        this.businessHours = businessHours;
        this.explanation = explanation;
        this.lat = lat;
        this.lng = lng;
        this.imageurl = imageurl;
    }

    public int getSEQ() { return SEQ; }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getAddr() {
        return addr;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getMenu() {
        return menu;
    }

    public String getTelNum() {
        return telNum;
    }

    public String getTitle() {
        return title;
    }

    public String getImageurl() {return imageurl; }

    public int findIndex(String title) {
        if(this.title.equals(title)) {
            return this.getSEQ();
        }
        else {
            return -1;
        }
    }
}
