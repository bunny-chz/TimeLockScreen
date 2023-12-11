package com.bunny.TimeLockScreen;

import android.annotation.TargetApi;
import android.content.Intent;
import android.service.quicksettings.TileService;
import android.util.Log;
/**
 * Project:  定时锁屏
 * JDK version used: <JDK1.8>
 * Author： Bunny     Github: https://github.com/bunny-chz/
 * Create Date：2022-10-25
 * Version: 1.0
 */

@TargetApi(24)
public class QSTile extends TileService {
    private static final String TAG = "QSTILE";
    @Override
    public void onTileAdded() {
        Log.i(TAG, "Method: onTileAdded()");
        super.onTileAdded();
    }
    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
        Log.i(TAG, "Method: onTileRemoved()");
    }
    @Override
    public void onStartListening() {
        super.onStartListening();
        Log.i(TAG, "Method: onStartListening()");
    }
    @Override
    public void onStopListening() {
        super.onStopListening();
        Log.i(TAG, "Method: onStopListening()");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Method: onCreate()");
    }
    @Override
    public void onClick() {
        super.onClick();
        Log.i(TAG, "Tile State: " + getQsTile().getState());
        if (!isLocked()) {
            updateTile();
        } else {
            unlockAndRun(new Runnable() {
                @Override
                public void run() {
                    updateTile();
                }
            });
        }
    }
    private void updateTile() {
        Intent intent = new Intent(getApplicationContext(),LockActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityAndCollapse(intent);
    }
}