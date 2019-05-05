package com.example.littlebrother;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public final class SpeedTracker implements LocationListener {

    // Current speed in metres per second.
    private double speed = 0;
    // Runs upon receiving location updates.
    private Runnable onUpdateSpeed = new Runnable() { public void run() { } };
    // Whether we are receiving data or not.
    private boolean running = false;
    // Last received location.
    private Location lastLocation = null;

    public double test = 0;

    private SpeedTracker(Context c) {
        LocationManager locationManager = (LocationManager)c.getSystemService(Context.LOCATION_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions((AppCompatActivity)c, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    // Upon receiving location updates.
    @Override public void onLocationChanged(Location location) {
        if (running) {
            //if (lastLocation != null)
                //speed = Math.sqrt(
                //        Math.pow(location.getLongitude() - lastLocation.getLongitude(), 2)
                //                + Math.pow(location.getLatitude() - lastLocation.getLatitude(), 2)
                //) / (location.getTime() - lastLocation.getTime());
            //if (location != null) {
            //    if (location.hasSpeed())
            //        speed = location.getSpeed();
            //    lastLocation = location;
            //}
            // Approximate change in latitude in metres.
            double yChange = 0;
            // Approximate change in longitude in metres.
            double xChange = 0;
            // Retrieve current geographical coordinates to calculate change in position.
            if (location != null && lastLocation != null) {
                yChange = location.getLatitude();
                speed = yChange;
            }
            lastLocation = location;
            onUpdateSpeed.run();
            test = speed;
        }
    }




    // Enable.
    public void begin() { running = true; }

    // Disable.
    public void cease() { running = false; }

    // Get last recorded speed.
    public double getSpeed() { return speed; }

    // Set action to run when speed updates.
    public void setOnUpdateSpeed(Runnable R) { onUpdateSpeed = R; }




    // These do nothing.
    @Override public void onStatusChanged(String p, int s, Bundle e) {
        switch(s)
        {
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("DEBUG", p + " out of service");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("DEBUG", p + " temp. unavailable");
                break;
            case LocationProvider.AVAILABLE:
                Log.d("DEBUG", p + " available");
                break;
            default:
        }
    }
    @Override public void onProviderEnabled(String p) { }
    @Override public void onProviderDisabled(String p) { }

    // Instance management.
    private static SpeedTracker i = null;
    public static synchronized void initialize(Context c) { i = new SpeedTracker(c); }
    public static synchronized SpeedTracker getInstance() { return i; }
}
