package com.example.littlebrother;

import android.Manifest;
import android.content.pm.PackageManager;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import static android.content.ContentValues.TAG;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;
import android.provider.Telephony;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MessageListener{

    // Length of each accelerometer sample in milliseconds.
    private static final int ACCUMULATOR_THRESHOLD = 2506;
    // Deviation of accelerometer sample must be < this to count as not moving.
    private static final double STILL_THRESHOLD = 0.45;
    // Deviation of accelerometer sample must be >= this to count as walking.
    private static final double WALKING_THRESHOLD_LOWER = 0.45;
    // Deviation of accelerometer sample must be < this to count as walking.
    private static final double WALKING_THRESHOLD_UPPER = 1;
    // Deviation of accelerometer sample must be >= this to count as running.
    private static final double RUNNING_THRESHOLD_LOWER = 1.4;
    // Deviation of accelerometer sample must be < this to count as running.
    private static final double RUNNING_THRESHOLD_UPPER = 2.2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Register sms listener
        SmsListener.bindListener(this);
        messageReceived("gamer");
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("6505551212", null, "sms message", null, null);
        
        // Register Accel
        Accel.initialize(this);
        Accel.getInstance().setOnReceive(onAccel);
        Accel.getInstance().begin();
    }

    @Override
    public void messageReceived(String message) {
        Toast.makeText(this, "New Message Received: " + message, Toast.LENGTH_SHORT).show();
    }

    private List<Double> sample = new ArrayList<>();
    private long accumulator = System.currentTimeMillis() + ACCUMULATOR_THRESHOLD;
    private double deviation = 0;

    // Runs on receiving accelerometer data.
    private Runnable onAccel = new Runnable() { public void run() {
        if (System.currentTimeMillis() < accumulator)
            sample.add(Accel.getInstance().getLinearAcceleration());
        else {
            deviation = Cruncher.deviation(sample);
            sample = new ArrayList<>();
            accumulator = System.currentTimeMillis() + ACCUMULATOR_THRESHOLD;
            if (deviation < STILL_THRESHOLD)
                ((TextView)findViewById(R.id.textView)).setText("DEVIATION_STILL: " + deviation);
            else if (deviation >= WALKING_THRESHOLD_LOWER && deviation < WALKING_THRESHOLD_UPPER)
                ((TextView)findViewById(R.id.textView)).setText("DEVIATION_WALKING: " + deviation);
            else if (deviation >= RUNNING_THRESHOLD_LOWER && deviation < RUNNING_THRESHOLD_UPPER)
                ((TextView)findViewById(R.id.textView)).setText("DEVIATION_RUNNING: " + deviation);
            else
                ((TextView)findViewById(R.id.textView)).setText("DEVIATION_RANDOM: " + deviation);
        }
    } };
}

