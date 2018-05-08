package com.afeka.agile.hitchikercollector;

public class Coordinates {

    String name;
    double latitude; // latitude
    double longitude; // longitude

    public Coordinates(String name, double latitude, double longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude=longitude;
    }

    public String getName(){
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setName(String newName){
        name=newName;
    }

    public void setLatitude(double newLatitude){
        latitude = newLatitude;
    }

    public void setLongitude(double newLonitude){
        longitude=newLonitude;
    }
}
