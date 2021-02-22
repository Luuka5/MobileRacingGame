package com.example.a2drace;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    static float width, height, scale;

    private MainThread thread;

    private Track track;
    private UI ui;

    public Game(Context context) {
        super(context);
        createStatic();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Game.scale = displayMetrics.widthPixels / 1000f;
        Game.width = displayMetrics.widthPixels / Game.scale;
        Game.height = displayMetrics.heightPixels / Game.scale;

        getHolder().addCallback(this);
        this.thread = new MainThread(getHolder(), this, 0);
        setFocusable(true);

        File.setContext(context);
        File.load(new String[] {
                "coins", "gems"
        });

        this.track = new Track(new Player(Game.redCar));
        this.ui = new UI();
    }

    public void update(float time) {
        this.track.update(time);
        this.ui.update(time, track);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.track.draw(canvas);
        this.ui.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        track.touch(e);
        if (e.getAction() == MotionEvent.ACTION_UP) ui.touch(e.getX() / Game.scale, e.getY() / Game.scale, track);
        return true;
    }

    static Resources res;
    static Bitmap redCar;
    static Bitmap blueCar;
    static Bitmap yellowCar;
    static Bitmap sideL;
    static Bitmap sideR;
    static Bitmap coin;
    static Bitmap gem;

    private void createStatic() {
        Game.res = getResources();
        Game.redCar = BitmapFactory.decodeResource(getResources(), R.drawable.car_red);
        Game.blueCar = BitmapFactory.decodeResource(getResources(), R.drawable.car_blue);
        Game.yellowCar = BitmapFactory.decodeResource(getResources(), R.drawable.car_yellow);

        Game.sideL = BitmapFactory.decodeResource(getResources(), R.drawable.side);
        Game.sideR = BitmapFactory.decodeResource(getResources(), R.drawable.side2);
        Game.coin = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        Game.gem = BitmapFactory.decodeResource(getResources(), R.drawable.gem);
    }
    public static Bitmap randomCar() {
        switch ((int) (Math.random() *3)) {
            case 1:
                return blueCar;
            case 2:
                return yellowCar;
            default:
                return redCar;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        track.pause = true;
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        track.pause = true;
    }
}
