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
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Register sms listener
        SmsListener.bindListener(this);
        messageReceived("gamer");
    }

    @Override
    public void messageReceived(String message) {
        if (message.substring(0,5).equals("*****")) {
            Toast.makeText(this, "New Message Received: " + message, Toast.LENGTH_SHORT).show();
        }
    }
}

