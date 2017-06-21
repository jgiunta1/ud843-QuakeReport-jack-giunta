package com.example.android.quakereport;

/**
 * Created by Jack on 2/1/2017.
 */

public class Earthquake {

    private double magnitude;
    private String location;
    private long TimeInMilliseconds;
    private String url;

    public Earthquake(double magnitude, String location, long date, String url){
        this.magnitude = magnitude;
        this.location = location;
        this.TimeInMilliseconds = date;
        this.url = url;
    }

    public long getTimeInMilliseconds() {
        return TimeInMilliseconds;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public String getUrl(){return url;}

}
