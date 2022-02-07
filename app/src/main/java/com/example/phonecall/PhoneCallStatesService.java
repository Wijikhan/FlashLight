package com.example.phonecall;
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

import java.security.Policy;
import java.util.Timer;
import java.util.TimerTask;

public class PhoneCallStatesService extends Service {

    private TelephonyManager telephonyManager;
    private PhoneStateListener listener;
    private boolean isOnCall;
    private  boolean flashmod;
    CountDownTimer timer;
    boolean isRunning = false;

    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isOnCall = false;
        flashmod = false;

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create a new PhoneStateListener
        listener = new PhoneStateListener() {

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                   if (isRunning) {
                       timer.cancel();
                   }

                            showToast("Call state: idle");
                            switchFlashLight(false);

                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        showToast("Call state: offhook");
                        isOnCall = true;
                        if (isRunning) {
                            timer.cancel();
                        }


                        switchFlashLight(false);

                        break;
                    case TelephonyManager.CALL_STATE_RINGING:

                        showToast("call state: ringing");
                        switchFlashLight(true);



                         timer = new CountDownTimer(10000, 500) {
                            @Override
                            public void onTick(long l) {
                                isRunning = true;
                                if (flashmod)
                                {
                                    Log.d("TAG", "turn off ");
                                    switchFlashLight(false);

                                }
                                else
                                {
                                    Log.d("TAG", "turn On ");

                                    switchFlashLight(true);
                                }


                            }

                            @Override
                            public void onFinish() {
                                isRunning = false;
                                Log.d("TAG", "onTick: on finish  ");

                            }

                        }.start();
                        break;

                        };









                }
            };


        // Register the listener with the telephony manager
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        return 1;
    }
    public void FlashOnOff(){
//        long delayLong = 20;
//        long timerValueLong = 100;
//        Timer timer;
//        final Policy.Parameters p = camera.getParameters();
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (!isOnCall) {
//                    p.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
//                    p.setFlashMode(Parameters.FLASH_MODE_TORCH);
//                    camera.setParameters(p);
//                    isLighOn = true;
//                } else {
//                    p.setFlashMode(Parameters.FLASH_MODE_OFF);
//                    camera.setParameters(p);
//                    isLighOn = false;
//                }
//            }
//        }, delayLong, timerValueLong);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void switchFlashLight(Boolean status) {
        flashmod = status;
        String[] mCameraId = null;
        CameraManager mCameraManager = (CameraManager) this.getSystemService(CAMERA_SERVICE);
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