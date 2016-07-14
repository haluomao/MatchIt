package com.atongmu.matchit.dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mfg on 16/07/05.
 */
public class Dao{
    private String name;
    private SharedPreferences sharedPreferences;
    private Context ctx;

    public Dao(Context ctx, String name){
        this.name = name;
        this.ctx = ctx;
        sharedPreferences = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void put(String key, String value){
        //存入数据
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void put(String key, int value){
        //存入数据
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public String getString(String key){
        return sharedPreferences.getString(key, "");
    }

    public int getInt(String key){
        return  sharedPreferences.getInt(key, -1);
    }
}
