package com.example.weathertasktry1.Model;

public class Wind
{
    private double speed;
    private float deg;

    public Wind(double speed, float deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public Wind() {
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public float getDeg() {
        return deg;
    }

    public void setDeg(float deg) {
        this.deg = deg;
    }
}
