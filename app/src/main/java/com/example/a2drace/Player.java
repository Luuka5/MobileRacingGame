package com.example.a2drace;

import android.graphics.Bitmap;
import android.view.MotionEvent;

public class Player extends Car {
    static float sensitivity = 1.1f;
    public float speed = 0f;
    public boolean mousePressed = false;

    public Player(Bitmap img) {
        super(500, 0, img, 0, 1f);
        friction = 0.05f;
        turningSpeed = 5;
    }

    @Override
    public void update(float time) {
        vel += speed;
        super.update(time);
        if (mousePressed) turn = 0;
        vel -= Math.abs(turn) * 0.1f;
        dir += turn;
    }

    public void reset() {
        speed = 0f;
        x = 500;
        y = 0;
        dir = 0;
        desX = x;
    }

    @Override
    protected float getA() {
        return (float) Math.atan2(x - desX, desY - Math.abs(x - desX) * 1.5);
    }

    public void control(MotionEvent e) {
        desX = (e.getX()/Game.scale -500) *Player.sensitivity + 500;
        if (e.getAction() == MotionEvent.ACTION_DOWN) mousePressed = true;
        if (e.getAction() == MotionEvent.ACTION_UP) mousePressed = false;
    }
}
