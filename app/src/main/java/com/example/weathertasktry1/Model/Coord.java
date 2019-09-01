package com.example.weathertasktry1.Model;

import androidx.annotation.NonNull;

public class Coord
{
    private float lon;
    private float lat;

    public Coord(float lon, float lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public Coord() {
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float     lat) {
        this.lat = lat;
    }

    @NonNull
    @Override
    public String toString() {
        return new StringBuilder("[").append(this.lat).append(',').append(this.lon).append(']').toString();
    }
}
