package com.example.lampemagique.controller;

import android.content.Intent;
import android.view.View;

import com.example.lampemagique.R;
import com.example.lampemagique.model.Light;
import com.example.lampemagique.view.AppBase;
import com.example.lampemagique.view.Home;
import com.example.lampemagique.view.MainActivity;

public class HomeController implements View.OnClickListener{
    public final static String STATE = "STATE";
    private final Home activity;

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
        switch (state){
            case 1:
                intent.putExtra(MainActivity.RED, 235);
                intent.putExtra(MainActivity.GREEN, 20);
                intent.putExtra(MainActivity.BLUE, 60);
                break;
            case 2:
                intent.putExtra(MainActivity.RED, 75);
                intent.putExtra(MainActivity.GREEN, 175);
                intent.putExtra(MainActivity.BLUE, 80);
                break;
            case 3:
                intent.putExtra(MainActivity.RED, 60);
                intent.putExtra(MainActivity.GREEN, 70);
                intent.putExtra(MainActivity.BLUE, 195);
                break;
            default:
                off = true;
                break;
        }
        intent.putExtra(STATE, off);
        activity.startActivity(intent);
    }
}
