package com.example.phonecall;

import static android.content.Context.CAMERA_SERVICE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.security.Policy;
import java.util.Timer;
import java.util.TimerTask;

public class callreciever extends BroadcastReceiver {

    private TelephonyManager telephonyManager;
    private PhoneStateListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String msg = "Ringing" + state;
        String picked = "OFFHOCK" + state;
        Log.e("hello", "i am here 4");
        if (state.equals("RINGING")) {

            switchFlashLight(context, true);
        } else if (state.equals("IDLE")) {
            switchFlashLight(context, false);
        }
        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {                                   // 4
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);  // 5
            // msg += ". Incoming number is " + incomingNumber;


            String myString = "0101010101";
            long blinkDelay = 50;

        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create a new PhoneStateListener
        listener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                     //   if (isOnCall) {
                         //   showToast("Call state: idle");
                          //  isOnCall = false;
                      //  }
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                     //   showToast("Call state: offhook");
                     //   isOnCall = true;
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                      //  showToast("call state: ringing");
                        break;
                }
            }
        };

        // Register the listener with the telephony manager
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        return 1;
    }

    private void switchFlashLight(Context context, Boolean status) {
        String[] mCameraId = null;
        CameraManager mCameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId[0], status);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}