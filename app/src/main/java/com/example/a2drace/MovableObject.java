package com.example.a2drace;

import android.graphics.Bitmap;

public class MovableObject extends Object {

    protected float vel;
    protected float friction = 0f;

    public MovableObject(int x, int y, float scale, Bitmap img) {
        super(x, y, (int) (img.getWidth() * scale), (int) (img.getHeight() * scale), img);
    }

    public void update(float time) {
        vel -= (vel * friction) * time;
        x -= Math.sin(getDirInRadians()) *vel *time;
        y -= Math.cos(getDirInRadians()) *vel *time;
    }
}
