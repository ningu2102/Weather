package com.example.weathertasktry1;


import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String api_key = "99e4ecfcb4be5b87a8c3271c68492288";
    public static Location current_location=null;
    public static float longitudeee;
    public static float latitudeee;

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH;mm EEE MM yyyy ");
        String formatted = simpleDateFormat.format(date);
        return  formatted;
    }

    public static String convertUnixToHour(long dt) {

        Date date = new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH;mm ");
        String formatted = simpleDateFormat.format(date);
        return  formatted;
    }
}
