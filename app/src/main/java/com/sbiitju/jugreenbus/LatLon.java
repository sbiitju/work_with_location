package com.sbiitju.jugreenbus;

public class LatLon {
    private String lat;
    private String lon;

    public LatLon(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public LatLon() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
