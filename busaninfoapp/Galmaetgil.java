package com.example.test;

public class Galmaetgil {
    private String gugan;
    private String startAddr;
    private String gmCourse;
    private String gmText;

    public Galmaetgil(String gugan, String startAddr, String gmCourse, String gmText) {
        this.gugan = gugan;
        this.startAddr = startAddr;
        this.gmCourse = gmCourse;
        this.gmText = gmText;
    }

    public String getGugan() {
        return gugan;
    }

    public void setGugan(String gugan) {
        this.gugan = gugan;
    }

    public String getStartAddr() {
        return startAddr;
    }

    public void setStartAddr(String startAddr) {
        this.startAddr = startAddr;
    }

    public String getGmCourse() {
        return gmCourse;
    }

    public void setGmCourse(String gmCourse) {
        this.gmCourse = gmCourse;
    }

    public String getGmText() {
        return gmText;
    }

    public void setGmText(String gmText) {
        this.gmText = gmText;
    }
}
