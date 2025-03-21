package com.example.lampemagique.model;

import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.example.lampemagique.R;

public class Light {
    private int red;
    private int green;
    private int blue;

    private boolean state;

    public Light(int r, int g, int b){
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public void setRed(int red) {
        this.red = Math.max(Math.min(red, 255), 0);
    }

    public void setGreen(int green) {
        this.green = Math.max(Math.min(green, 255), 0);
    }

    public void setBlue(int blue) {
        this.blue = Math.max(Math.min(blue, 255), 0);
    }

    public void setColor(int color){
        this.red = Color.red(color);
        this.green = Color.green(color);
        this.blue = Color.blue(color);
    }

    public int getColor(){
        return Color.rgb(red, green, blue);
    }

    public boolean isOn() {
        return state;
    }

    public void setState(boolean b) {
        this.state = b;
    }
}
