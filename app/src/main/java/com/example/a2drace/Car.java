package com.example.a2drace;

import android.graphics.Bitmap;

public class Car extends MovableObject {
    public float desX, desY = 500;
    protected float turn, turningSpeed = 5;

    public Car(int x, int y, Bitmap car, float dir, float velocity) {
        super(x, y, 0.3f *Game.scale, car);
        this.desX = x;
        this.dir = dir;
        if (dir < -90 || dir > 90) desY *= -1;
        this.vel = velocity;
    }

    @Override
    public void update(float time) {
        float a = (float) (getA() / (Math.PI *2) * 360) *0.75f;
        turn = (a - dir) *time;
        if (turn < -turningSpeed*time) turn = -turningSpeed*time;
        if (turn > turningSpeed*time) turn = turningSpeed*time;
        float b = turn * vel * 0.05f;
        dir = Math.abs(dir - a) <= Math.abs(b) ? a : dir + b;

        super.update(time);
    }

    protected float getA() {
        return (float) Math.atan2(x - desX, desY);
    }
}
