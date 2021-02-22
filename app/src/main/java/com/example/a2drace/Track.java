package com.example.a2drace;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

public class Track {

    private ArrayList<Coin> coins;
    private ArrayList<Car>[] lanes;
     Player car; //!!!
    private float dist;

    private Bitmap side, side2;
    private Paint line, text;

    private float cameraY = 0;

    private int lane = 0, target = 0;

    public float gameOver = -1;
    private boolean wall = false;

    public boolean pause = true, revived = false;

    public Track(Player p) {
        this.car = p;
        this.coins = new ArrayList<>();
        this.lanes = new ArrayList[4];
        for (int i = 0; i < this.lanes.length; i++) {
            this.lanes[i] = new ArrayList<>();
        }

        this.side = Bitmap.createScaledBitmap(Game.sideL, (int) (100 * Game.scale), (int) (100 * Game.scale), false);
        this.side2 = Bitmap.createScaledBitmap(Game.sideR, (int) (100 * Game.scale), (int) (100 * Game.scale), false);

        this.line = new Paint();
        this.line.setColor(Color.WHITE);
        this.text = new Paint();
        this.text.setColor(Color.WHITE);
        this.text.setTextSize(150*Game.scale);
    }

    public void update(float time) {
        if (gameOver != -1) {
            gameOver += 50 *time;
            gameOver = gameOver > 255 ? 255 : gameOver;
            return;
        }

        if (pause) return;

        car.update(time);
        if (car.collision(0) || car.collision(1000)) {
            wall = true;
            gameOver();
        }
        cameraY += (car.y - cameraY) / 5f;

        for (int i = 0; i < lanes.length; i++) {
            for (int j = 0; j < lanes[i].size(); j++) {
                lanes[i].get(j).update(time);
                lanes[i].get(j).desX += (((Math.random() *100f -50f)*time - (lanes[i].get(j).x - (200+i*200))) / 2f);

                if (car.collision(lanes[i].get(j))) {
                    gameOver();
                    lanes[i].get(j).vel = 0;
                }

                if (lanes[i].get(j).y -Game.height > cameraY) lanes[i].remove(j);
            }
        }

        for (int i = 0; i < coins.size(); i++) {
            if (car.collision(coins.get(i))) {
                if (coins.get(i).gem) {
                    File.changeInt("gems", 1);
                } else {
                    File.changeInt("coins", 1);
                }
                coins.remove(i);
            } else if (coins.get(i).y -Game.height > cameraY) coins.remove(i);
        }

        dist -= 65f *time;
        car.speed = 1.5f+ (cameraY / -750000f);
        if (dist > cameraY) {
            dist -= 600;
            generate();
        }
    }

    private void generate() {
        int count = 0;
        int x = 200 +(int) (Math.random() *4) * 200;
        int y = (int) (cameraY -Game.height) - (int) (Math.random() * 500);
        while (Math.random() < 0.5) {
            count++;
            coins.add(new Coin(x, y +count*30, 100, 100, false));
            if (count > 10) break;
        }

        int newLane = lane;
        if (lane < target) newLane++;
        if (lane > target) newLane--;

        for (int i = 0; i < lanes.length; i++) {
            if (i == lane || i == newLane) {
                if (count == 0 && Math.random() < 0.01) coins.add(new Coin(200+i*200, (int) (cameraY -Game.height) - 700, 100, 100, true));
                continue;
            }
            addCar(i, (int) (cameraY -Game.height) - (int) (Math.random() * 350));
        }

        if (lane == target) target = (int) (Math.random() * 4);
        this.lane = newLane;
    }

    private void addCar(int lane, int y) {
        if (Math.random() < 0.1) return;
        this.lanes[lane].add(new Car(200 + lane * 200, y,
                Game.randomCar(), 0, 65f) );
    }


    public void draw(Canvas canvas) {
        canvas.drawColor(Game.res.getColor(R.color.black));

        float distG = -cameraY % 400;
        for (int i = -400; i < Game.height +100; i += 400) {
            for (int j = i; j < i +400; j += 100) {
                canvas.drawBitmap(side, 0,  (int) ((distG + j) *Game.scale), new Paint());
                canvas.drawBitmap(side2, 900 *Game.scale,  (int) ((distG + j) *Game.scale), new Paint());
            }

            for (int j = 0; j < 3; j++) {
                int x = (int) (  (300 + j*200 -5)  *Game.scale );
                int y = (int) (  (distG + i)  *Game.scale );
                int w = (int) (  10 *Game.scale );
                int h = (int) (  (j == 1 ? 450 : 100) *Game.scale );
                canvas.drawRect(new Rect(x, y, x + w, y + h), this.line);
            }
        }

        for (Object c : this.coins) {
            c.draw(canvas, 0, -cameraY +(Game.height * 0.85f));
        }

        for (ArrayList<Car> arr : this.lanes) {
            for (int i = 0; i < arr.size(); i++) {
                arr.get(i).draw(canvas, 0, -cameraY +(Game.height * 0.85f));
            }
        }
        car.draw(canvas, 0, -cameraY +(Game.height * 0.85f));

        if (gameOver != -1) canvas.drawColor(Color.argb((int) gameOver, 0, 0, 0));
    }

    public int getDist() {
        return ((int) ((cameraY) / -100f));
    }

    private void gameOver() {
        pause = true;
        gameOver = 0;
    }

    public void secondChange() {
        if (car.x < 300) car.x = 300;
        if (car.x > 700) car.x = 700;
        car.desX = car.x;
        for (int i = 0; i < lanes.length; i++) {
            lanes[i] = new ArrayList<>();
        }
        if (wall) {
            car.dir = 0;
        }

        wall = false;
        gameOver = -1;
        revived = true;
        pause = false;
        car.speed = 0;
        car.vel = 0;
    }

    public void newGame() {
        car.reset();
        cameraY = 0;
        dist = 0;
        this.coins = new ArrayList<>();
        this.lanes = new ArrayList[4];
        for (int i = 0; i < this.lanes.length; i++) {
            this.lanes[i] = new ArrayList<>();
        }
        gameOver = -1;
        revived = false;
        car.speed = 0;
        car.vel = 0;
    }

    public void touch(MotionEvent e) {
        if (gameOver != -1 || pause) return;
        car.control(e);
    }
}
