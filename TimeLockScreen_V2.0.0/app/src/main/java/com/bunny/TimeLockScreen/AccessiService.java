package com.bunny.TimeLockScreen;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

/**
 * Project:  定时锁屏 V2.0
 * Comments: AccessibilityService后台服务类
 * JDK version used: <JDK1.8>
 * Author： Bunny     Github: https://github.com/bunny-chz/
 * Create Date：2023-09-03
 * Version: 1.0
 */

public class AccessiService extends AccessibilityService {
    private static final String TAG = "LOCK_SCREEN_LOG_TAG";
    private boolean isLoop = false;
    private long targetTime = 0;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        StartThreadGesture();
    }
    //开启一个子线程
    private void StartThreadGesture() {
        new Thread(){
            @Override
            public void run() {
                do {
                    try {
                        Thread.sleep(900);
                        Message message=new Message();
                        message.what=1;
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);
            }
        }.start();
    }


    @SuppressLint("HandlerLeak")
    private final Handler handler=new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                if (isLoop){
                    if (System.currentTimeMillis() >= targetTime){
                        lockScreen();
                        Log.d(TAG,"time ---> " + System.currentTimeMillis() + "targetTime ----> " + targetTime);
                        isLoop = false;
                    }
                }
                Log.d(TAG,"time ---> " + System.currentTimeMillis());
            }
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "AccessibilityEvent end.");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(getApplicationContext(), "无障碍服务开启成功，若出现错误，请重启软件并重开服务！", Toast.LENGTH_LONG).show();
        SaveData saveData = new SaveData(this);
        targetTime =  saveData.loadLong("TargetTime") + System.currentTimeMillis();
        isLoop = true;
        Log.d(TAG,"onServiceConnected: success.");
    }

    private void lockScreen() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null && powerManager.isInteractive()) {
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
            if (devicePolicyManager != null) {
                ComponentName componentName = new ComponentName(this, ServiceReceiver.class);
                if (devicePolicyManager.isAdminActive(componentName)) {
                    devicePolicyManager.lockNow();
                }
            }
        }
    }
}
