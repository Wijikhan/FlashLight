package com.example.phonecall;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import android.content.Intent;

import android.os.Handler;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    smsreader smsreader = new smsreader();


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View btnStart = findViewById(R.id.btn_start);
        View btnStop = findViewById(R.id.btn_stop);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SMS_RECEIVED");



        btnStart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PhoneCallStatesService.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(i);
                }
                else
                {
                    startService(i);
                }
                IntentFilter intentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
                    registerReceiver(smsreader, intentFilter);

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this, PhoneCallStatesService.class));
                unregisterReceiver(smsreader);
            }
        });
    }
}