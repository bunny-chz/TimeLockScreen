package com.bunny.TimeLockScreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
/**
 * Project:  定时锁屏
 * JDK version used: <JDK1.8>
 * Author： Bunny     Github: https://github.com/bunny-chz/
 * Create Date：2022-10-25
 * Version: 1.0
 */
public class LockActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_activity);
        DevicePolicyManager policyManager;
        policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ComponentName mDeviceAdminSample = new ComponentName(this,ServiceReceiver.class);
        if(policyManager.isAdminActive(mDeviceAdminSample))
        {
            policyManager.lockNow();
        }
        else
        {
            Toast.makeText(this,"请先授予软件锁屏的权限",Toast.LENGTH_SHORT).show();
        }
        //返回桌面并关闭软件
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

