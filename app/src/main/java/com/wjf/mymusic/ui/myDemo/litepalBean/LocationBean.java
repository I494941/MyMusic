package com.wjf.mymusic.ui.myDemo.litepalBean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by wjf on 2019/4/3.
 */
public class LocationBean extends LitePalSupport implements Serializable {

    private double lng;
    private double lat;
    private double distance;
    private String time;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {

        return "time = " + time + ",lng = " + lng + ", lat = " + lat + ", distance = " + distance;
    }
}
