package com.example.a2drace;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private Game game;
    private SurfaceHolder holder;
    private Canvas canvas;
    private boolean running = true;

    private long prev;
    private int millis;

    public MainThread(SurfaceHolder holder, Game game, int millis) {
        super();
        this.holder = holder;
        this.game = game;
        this.millis = millis;
        this.prev = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while(running) {
            canvas = null;
            try {
                canvas = holder.lockCanvas();
                synchronized (holder) {
                    long now = System.currentTimeMillis();
                    while (now - millis < prev) {
                        Thread.sleep(1);
                        now = System.currentTimeMillis();
                    }

                    game.update((float) (now - prev) / 100);
                    game.draw(canvas);
                    prev = now;
                }

            } catch(Exception ex) {
                ex.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        holder.unlockCanvasAndPost(canvas);
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public void setRunning(boolean running) {
        this.running =  running;
    }
}
