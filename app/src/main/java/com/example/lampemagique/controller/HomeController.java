package com.example.lampemagique.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lampemagique.R;
import com.example.lampemagique.model.Light;
import com.example.lampemagique.view.AppBase;
import com.example.lampemagique.view.Home;
import com.example.lampemagique.view.MainActivity;
import androidx.preference.PreferenceManager;


public class HomeController implements View.OnClickListener{
    public final static String STATE = "STATE";
    private final Home activity;

    public static final int DEFAULT_1 = -1371076;
    public static final int DEFAULT_2 = -11817136;
    public static final int DEFAULT_3 = -12826941;

    public HomeController(Home activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.first){
            launchLight(1);
        }else if(v.getId() == R.id.second){
            launchLight(2);
        }else if(v.getId() == R.id.third){
            launchLight(3);
        }else if(v.getId() == R.id.off){
            launchLight(-1);
        }
    }

    public void launchLight(int state){
        Intent intent = new Intent(activity, MainActivity.class);
        boolean off = false;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        int color;
        switch (state){
            case 1:
                color = preferences.getInt(activity.getString(R.string.preset1), DEFAULT_1);
                intent.putExtra(MainActivity.RED, Color.red(color));
                intent.putExtra(MainActivity.GREEN, Color.green(color));
                intent.putExtra(MainActivity.BLUE, Color.blue(color));
                break;
            case 2:
                color = preferences.getInt(activity.getString(R.string.preset2), DEFAULT_2);
                intent.putExtra(MainActivity.RED, Color.red(color));
                intent.putExtra(MainActivity.GREEN, Color.green(color));
                intent.putExtra(MainActivity.BLUE, Color.blue(color));
                break;
            case 3:
                color = preferences.getInt(activity.getString(R.string.preset3), DEFAULT_3);
                intent.putExtra(MainActivity.RED, Color.red(color));
                intent.putExtra(MainActivity.GREEN, Color.green(color));
                intent.putExtra(MainActivity.BLUE, Color.blue(color));
                break;
            default:
                off = true;
                break;
        }
        intent.putExtra(STATE, off);
        activity.startActivity(intent);
    }

    public int getPreset1(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        return preferences.getInt(activity.getString(R.string.preset1), DEFAULT_1);
    }

    public int getPreset2(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        return preferences.getInt(activity.getString(R.string.preset2), DEFAULT_2);
    }

    public int getPreset3(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        return preferences.getInt(activity.getString(R.string.preset3), DEFAULT_3);
    }
}
