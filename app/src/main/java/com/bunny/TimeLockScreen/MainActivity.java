package com.bunny.TimeLockScreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bunny.TimeLockScreen.sshadkany.neo;
import com.bunny.TimeLockScreen.sshadkany.RectButton;
/**
 * Project:  定时锁屏
 * JDK version used: <JDK1.8>
 * Author： Bunny     Github: https://github.com/bunny-chz/
 * Create Date：2022-10-25
 * Version: 1.0
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "LOCK_SCREEN_LOG_TAG";

    private int hour;
    private int min;
    private int sec;
    private int allTime;

    private long targetTime = 0;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    EditText secondEt, minEt, hourEt;

    TextView finalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RectButton activateBtn = findViewById(R.id.activateBtn);
        RectButton setTimeBtn = findViewById(R.id.setTimeBtn);
        RectButton testLockBtn = findViewById(R.id.testLockBtn);
        secondEt = findViewById(R.id.secondEt);
        minEt = findViewById(R.id.minEt);
        hourEt = findViewById(R.id.hourEt);
        finalTime = findViewById(R.id.finalTime);

//        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent intent = new Intent(this, LockActivity.class);
//        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

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
                //查看权限
                DevicePolicyManager policyManager;
                policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
                ComponentName mDeviceAdminSample = new ComponentName(MainActivity.this,ServiceReceiver.class);
                if(policyManager.isAdminActive(mDeviceAdminSample))
                {
                    targetTime = (hour * 60 * 60 + min * 60 + sec) * 1000;
                    finalTime.setText("已设置的时间是 " + hour + "小时 " + min + "分 " + sec + "秒\n\n屏幕将在 " + targetTime/1000 + " 秒后关闭");
//                    allTime = (hour * 60 * 60 + min * 60 + sec);
//                    Log.d(TAG,"allTime ---> " + allTime);
//                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (allTime * 1000), pendingIntent);
//                    Toast.makeText(MainActivity.this, "定时设置完成，手机会在 " + allTime
//                            + " 秒后锁屏 ", Toast.LENGTH_SHORT).show();
//                    Log.d(TAG,"System.currentTimeMillis() + allTime ---> " + System.currentTimeMillis() + allTime);
//                    Log.d(TAG,"currentTime ---> " + (System.currentTimeMillis() + allTime)/60/60 );
//                    finalTime.setText("已设置的时间是 " + hour + "小时 " + min + "分 " + sec + "秒\n\n屏幕将在 " + allTime + " 秒后关闭");
                    //                //返回桌面
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"请先授予软件锁屏的权限",Toast.LENGTH_SHORT).show();
                    activatePermission();
                }
            }}
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

    @Override
    protected void onPause() {
        super.onPause();
        if(targetTime > 0){
            try {
                Thread.sleep(targetTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            DevicePolicyManager policyManager;
            policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
            ComponentName mDeviceAdminSample = new ComponentName(this,ServiceReceiver.class);
            if(policyManager.isAdminActive(mDeviceAdminSample))
            {
                policyManager.lockNow();
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }

        Log.d(TAG,"targetTime ---> " + targetTime);
        Log.d(TAG,"onPause ---> ");

    }

    public void activatePermission(){
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        ComponentName mDeviceAdminSample = new ComponentName(this,ServiceReceiver.class);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "请先授予软件锁屏的权限，否则软件无法正常执行功能");
        startActivity(intent);
    }

}
