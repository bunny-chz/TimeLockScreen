package com.bunny.TimeLockScreen;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Project:  定时锁屏 V2.0
 * Comments: SharedPreferences保存键值对工具类
 * JDK version used: <JDK1.8>
 * Create Date：2023-09-03
 * Version: 1.0
 */

public class SaveData {
    private final Context context;
    public SaveData(Context context){
        this.context = context;
    }
    /**
     * SharedPreferences保存 long 键值对
     *
     * @param value 保存的值
     * @param key 保存的键
     */
    public void saveLong(long value,String key){
        String name = context.getResources().getString(R.string.SaveData);
        SharedPreferences shp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putLong(key,value);
        editor.apply();
    }
    /**
     * SharedPreferences保存 long 键值对
     *
     * @param key 要加载的键
     */
    public long loadLong(String key){
        String name = context.getResources().getString(R.string.SaveData);
        SharedPreferences shp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return shp.getLong(key,10000);//默认返回 10
    }
    /**
     * SharedPreferences保存 boolean 键值对
     *
     * @param value 保存的值
     * @param key 保存的键
     */
    public void saveBoolean(boolean value,String key){
        String name = context.getResources().getString(R.string.SaveData);
        SharedPreferences shp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    /**
     * SharedPreferences保存 Long 键值对
     *
     * @param key 要加载的键
     */
    public boolean loadBoolean(String key){
        String name = context.getResources().getString(R.string.SaveData);
        SharedPreferences shp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return shp.getBoolean(key,false);//默认返回 10
    }
}