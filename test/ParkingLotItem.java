package com.example.test;

public class ParkingLotItem {
    String pkName, pkAddr, pkTelNum;
    //주차장 유형
    String pkGubun;
    // 주차 구획수
    String pkCnt;
    //평일 운영 시작/종료
    String svcStTm, svcEndTm;
    //토요일 운영 시작/종료
    String satStTm, satEndTm;
    //공휴일 운영 시작/종료
    String hlStTm, hlEndTm;
    //주차 기본요금
    String tenMin;
    //1일 주차요금
    String ftDay;
    // 운영 요일
    String oprDay;
    // 기본 시간
    String pkBaseTime;
    //추가 시간
    String pkAddTime;
    //추가 요금
    String feeAdd;
    // 결제 방법
    String payMtd;
    String doroAddr;

    double latP;
    double lngP;

    public ParkingLotItem(String pkName, String pkAddr, String pkTelNum, String pkGubun, String pkCnt,
                          String svcStTm, String svcEndTm, String satStTm, String satEndTm, String hlStTm ,
                          String hlEndTm, String tenMin, String ftDay, String oprDay, String pkBaseTime,
                          String pkAddTime, String feeAdd, String payMtd, String doroAddr) {
        this.pkName = pkName;
        this.pkAddr = pkAddr;
        this.pkTelNum = pkTelNum;
        this.pkGubun = pkGubun;
        this.pkCnt = pkCnt;
        this.svcStTm = svcStTm;
        this.svcEndTm = svcEndTm;
        this.satStTm = satStTm;
        this.satEndTm = satEndTm;
        this.hlEndTm = hlEndTm;
        this.hlStTm = hlStTm;
        this.tenMin = tenMin;
        this.ftDay = ftDay;
        this.oprDay = oprDay;
        this.pkBaseTime = pkBaseTime;
        this.pkAddTime = pkAddTime;
        this.feeAdd = feeAdd;
        this.payMtd = payMtd;
        this.doroAddr = doroAddr;
        this.latP = 0.0;
        this.lngP = 0.0;
    }

    public String getPkName() { return pkName; }
    public String getPkAddr() { return pkAddr; }
    public String getPkTelNum() { return pkTelNum; }
    public String getPkGubun() { return pkGubun; }
    public String getPkCnt() { return  pkCnt; }
    public String getSvcStTm() { return svcStTm; }
    public String getSvcEndTm() { return svcEndTm; }
    public String getSatStTm() { return satStTm; }
    public String getSatEndTm() { return satEndTm; }
    public String getHlStTm() { return hlStTm; }
    public String getHlEndTm() { return hlEndTm; }
    public String getTenMin() { return tenMin; }
    public String getFtDay() { return ftDay; }
    public String getOprDay() { return oprDay; }
    public String getPkBaseTime() { return pkBaseTime; }
    public String getPkAddTime() { return pkAddTime; }
    public String getFeeAdd() { return feeAdd; }
    public String getPayMtd() { return payMtd; }
    public String getDoroAddr() { return doroAddr; }

    public double getLatP() {
        return latP;
    }

    public double getLngP() {
        return lngP;
    }

    public void setLngP(double lng) {
        this.lngP = lng;
    }

    public void setLatP(double lat) {
        this.latP = lat;
    }
}
