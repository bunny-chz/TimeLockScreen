package com.bunny.TimeLockScreen;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
/**
 * Project:  定时锁屏
 * JDK version used: <JDK1.8>
 * Author： Bunny     Github: https://github.com/bunny-chz/
 * Create Date：2022-10-25
 * Version: 1.0
 */
public class ServiceReceiver extends DeviceAdminReceiver {
    private final String TAG = "LOCK_SCREEN_LOG_TAG";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG,"onReceive ---> ");
    }
    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.d(TAG,"onEnabled ---> ");
        super.onEnabled(context, intent);
    }
    @Override
    public void onDisabled(Context context, Intent intent) {
        Log.d(TAG,"onDisabled ---> ");
        super.onDisabled(context, intent);
    }
}
