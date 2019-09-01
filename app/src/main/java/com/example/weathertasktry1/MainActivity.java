package com.example.weathertasktry1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathertasktry1.Fragments.CityFragment;
import com.example.weathertasktry1.Fragments.ForecastFragment;
import com.example.weathertasktry1.Fragments.TodayFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private GpsTracker gpsTracker;
    private TextView tvLatitude, tvLongitude;
    String lat,lon;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //new
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//         tvLatitude = (TextView) findViewById(R.id.latitude);
//         tvLongitude = (TextView) findViewById(R.id.longitude);
//


        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();


        }
        getLocation();
        Log.d("v1",lat);
        Log.d("v2",lon);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(TodayFragment.getInstance(),"TODAY");
        viewPagerAdapter.addFragment(ForecastFragment.getInstance(),"5 Days");
        viewPagerAdapter.addFragment(CityFragment.getInstance(),"City");
        viewPager.setAdapter(viewPagerAdapter);
    }

    public void getLocation(){
        gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            float latitude = (float)gpsTracker.getLatitude();
            float longitude = (float)gpsTracker.getLongitude();
//            tvLatitude.setText(String.valueOf(latitude));
//            tvLongitude.setText(String.valueOf(longitude));
            Common.latitudeee=latitude;
            Common.longitudeee=longitude;
            lat = String.valueOf(latitude);
            lon = String.valueOf(longitude);
            Log.d("dataaa",String.valueOf(longitude));
        }else{
            gpsTracker.showSettingsAlert();
        }
    }
}

