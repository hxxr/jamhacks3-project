package com.example.littlebrother;

import android.Manifest;
import android.content.pm.PackageManager;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import static android.content.ContentValues.TAG;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.telephony.SmsManager;
import android.provider.Telephony;
import android.net.Uri;


public class MainActivity extends Activity implements MessageListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Register sms listener
        SmsListener.bindListener(this);
        messageReceived("gamer");
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("6505551212", null, "sms message", null, null);
        
        //Register Accel
        Accel.initialize(getApplicationContext());
        Accel.getInstance().setOnReceive(onAccel);
        Accel.getInstance().begin();
    }

    @Override
    public void messageReceived(String message) {
        Toast.makeText(this, "New Message Received: " + message, Toast.LENGTH_SHORT).show();
    }
    
    // Runs on receiving accelerometer data.
    private Runnable onAccel = new Runnable() { public void run() {
        Log.d("ACCEL", Double.toString(Accel.getInstance().getLinearAcceleration()));
    } };
}

