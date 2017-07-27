package com.example.ibrahim_01.locationapp;

/**
 * Created by ibrahim-01 on 7/17/2017.
 */

public class userClass {


    private String fullName;
    private double latitude;
    private double longitude;
    private double phoneNo;
    private boolean status;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(double phoneNo) {
        this.phoneNo = phoneNo;
    }



    public userClass(String fullName, double latitude, double longitude, double phoneNo, boolean status) {

        this.fullName = fullName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNo = phoneNo;
        this.status = status;
    }

    public userClass(String fullName, double latitude, double longitude ) {

        this.fullName = fullName;
        this.latitude = latitude;
        this.longitude = longitude;

    }



    public userClass(){

    }




}
