package com.example.phonecall;

import static android.content.Context.CAMERA_SERVICE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

public class smsreader extends BroadcastReceiver {
    boolean flashmod = false;
    @Override

    public void onReceive(Context context, Intent intent)
    {
        Log.d("TAG", "onReceive: we have recieve message ");


      //  switchFlashLight(context, true);


        CountDownTimer timer = new CountDownTimer(2000, 200) {
            @Override
            public void onTick(long l) {

                if (flashmod) {
                    Log.d("TAG", "turn off ");
                    switchFlashLight(context, false);

                } else {
                    Log.d("TAG", "turn On ");

                    switchFlashLight(context, true);
                }


            }

            @Override
            public void onFinish() {
                switchFlashLight(context, false);


                Log.d("TAG", "onTick: on finish  ");

            }
        }.start();



    }
    private void switchFlashLight(Context context, Boolean status) {
        flashmod = status;
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
