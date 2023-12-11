package com.bunny.TimeLockScreen;

import android.app.admin.DevicePolicyManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bunny.TimeLockScreen.sshadkany.RectButton;

/**
 * Project:  定时锁屏 V2.0
 * Comments: MainActivity类
 * JDK version used: <JDK1.8>
 * Author： Bunny     Github: https://github.com/bunny-chz/
 * Create Date：2023-09-03
 * Version: 1.0
 */

public class MainActivity extends AppCompatActivity {

    private final String TAG = "LOCK_SCREEN_LOG_TAG";

    private int hour;
    private int min;
    private int sec;
    private long targetTime = 1;

    EditText secondEt, minEt, hourEt;
    TextView finalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RectButton activateBtn = findViewById(R.id.activateBtn);
        RectButton setTimeBtn = findViewById(R.id.setTimeBtn);
        RectButton testLockBtn = findViewById(R.id.testLockBtn);
        RectButton thirtyMinBtn = findViewById(R.id.thirtyMinBtn);
        secondEt = findViewById(R.id.secondEt);
        minEt = findViewById(R.id.minEt);
        hourEt = findViewById(R.id.hourEt);
        finalTime = findViewById(R.id.finalTime);
        SaveData saveData = new SaveData(this);
        if (!saveData.loadBoolean("isFirstStartAlertDialog")){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(false);
            builder.setTitle("重要提示");
            builder.setMessage("使用前问问自己是否记得锁屏密码？！不然别点确定！！！");
            builder.setPositiveButton("确定记得", (dialogInterface, i) -> {
                saveData.saveBoolean(true,"isFirstStartAlertDialog");
            });
            builder.setNegativeButton("不记得", (dialogInterface, i) -> {
                this.finish();
            });
            builder.create().show();
        }
        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(secondEt.getText().toString()) ^ TextUtils.isEmpty(minEt.getText().toString()) ^ TextUtils.isEmpty(hourEt.getText().toString())){
                    Toast.makeText(MainActivity.this, "至少输入一个时间值", Toast.LENGTH_SHORT).show();
                }else {
                    if(secondEt.getText().toString().equals("")){
                        sec = 0;
                    }
                    else{
                        sec = Integer.parseInt(secondEt.getText().toString());
                    }
                    if(minEt.getText().toString().equals("")){
                        min = 0;
                    }
                    else{
                        min = Integer.parseInt(minEt.getText().toString());
                    }
                    if(hourEt.getText().toString().equals("")){
                        hour = 0;
                    }
                    else{
                        hour = Integer.parseInt(hourEt.getText().toString());
                    }
                Log.d(TAG,"sec ---> " + sec + "min ---> " + min + "hour ---> " + hour);
                DevicePolicyManager policyManager;
                policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
                ComponentName mDeviceAdminSample = new ComponentName(MainActivity.this,ServiceReceiver.class);
                if(policyManager.isAdminActive(mDeviceAdminSample))
                {
                    targetTime = (hour * 60 * 60 + min * 60 + sec) * 1000;
                    Log.d(TAG,"targetTime ------> " + targetTime);
                    SaveData saveData = new SaveData(MainActivity.this);
                    saveData.saveLong(targetTime,"TargetTime");
                    finalTime.setText("已设置的时间是 " + hour + "小时 " + min + "分 " + sec + "秒\n屏幕将在 " + (hour * 60 * 60 + min * 60 + sec)  + " 秒后关闭");
                    if (!saveData.loadBoolean("isFirstServiceAlertDialog")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle("重要提示");
                        builder.setMessage("本功能需开启无障碍服务才可正常使用，请跳转后找到无障碍服务“定时锁屏”一项，开启开关（每个安卓手机的显示可能不一样）！后面返回本软件主界面，然后就可以后台定时了。");
                        builder.setPositiveButton("确定", (dialogInterface, i) -> {
                            Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                            startActivity(accessibleIntent);
                            saveData.saveBoolean(true,"isFirstServiceAlertDialog");
                        });
                        builder.setNegativeButton("不了", (dialogInterface, i) -> {
                            Toast.makeText(MainActivity.this,"请先开启无障碍服务",Toast.LENGTH_SHORT).show();
                        });
                        builder.create().show();
                    }else{
                        Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(accessibleIntent);
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"请先授予软件锁屏的权限",Toast.LENGTH_SHORT).show();
                    activatePermission();
                }
            }}
        });
        thirtyMinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sec = 0;
                min = 30;
                hour = 0;
                Log.d(TAG,"sec ---> " + sec + "min ---> " + min + "hour ---> " + hour);
                DevicePolicyManager policyManager;
                policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
                ComponentName mDeviceAdminSample = new ComponentName(MainActivity.this,ServiceReceiver.class);
                if(policyManager.isAdminActive(mDeviceAdminSample))
                {
                    targetTime = (hour * 60 * 60 + min * 60 + sec) * 1000;
                    Log.d(TAG,"targetTime ------> " + targetTime);
                    SaveData saveData = new SaveData(MainActivity.this);
                    saveData.saveLong(targetTime,"TargetTime");
                    finalTime.setText("已设置的时间是 " + hour + "小时 " + min + "分 " + sec + "秒\n屏幕将在 " + (hour * 60 * 60 + min * 60 + sec) + " 秒后关闭");
                    if (!saveData.loadBoolean("isFirstServiceAlertDialog")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle("重要提示");
                        builder.setMessage("本功能需开启无障碍服务才可正常使用，请跳转后找到无障碍服务“定时锁屏”一项（可能每个手机的显示方式有差别），开启开关！后面返回本软件主界面，然后就可以后台定时了。");
                        builder.setPositiveButton("确定", (dialogInterface, i) -> {
                            Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                            startActivity(accessibleIntent);
                            saveData.saveBoolean(true,"isFirstServiceAlertDialog");
                        });
                        builder.setNegativeButton("不了", (dialogInterface, i) -> {
                            Toast.makeText(MainActivity.this,"请先开启无障碍服务",Toast.LENGTH_SHORT).show();
                        });
                        builder.create().show();
                    }else{
                        Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(accessibleIntent);
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"请先授予软件锁屏的权限",Toast.LENGTH_SHORT).show();
                    activatePermission();
                }
            }
        });
        activateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DevicePolicyManager policyManager;
                policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
                ComponentName mDeviceAdminSample = new ComponentName(MainActivity.this,ServiceReceiver.class);
                if(policyManager.isAdminActive(mDeviceAdminSample))
                {
                    Toast.makeText(MainActivity.this, "已授予权限！", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    activatePermission();
                }
            }
        });
        testLockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DevicePolicyManager policyManager;
                policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
                ComponentName mDeviceAdminSample = new ComponentName(MainActivity.this,ServiceReceiver.class);
                if(policyManager.isAdminActive(mDeviceAdminSample))
                {
                    Intent intent = new Intent(MainActivity.this,LockActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"请先授予软件锁屏的权限",Toast.LENGTH_SHORT).show();
                    activatePermission();
                }
            }
        });
    }

    public void activatePermission(){
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        ComponentName mDeviceAdminSample = new ComponentName(this,ServiceReceiver.class);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "请先授予软件锁屏的权限，否则软件无法正常执行功能");
        startActivity(intent);
    }
}