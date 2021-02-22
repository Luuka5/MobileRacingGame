package com.example.a2drace;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;

public class UI {
    private ArrayList<UIElement> elements;

    public final Text dist, dist2, gems, coins, message;
    public final Button play, menu;
    public final Frame con;

    private float msgTime = 0;

    public UI() {
        this.elements = new ArrayList<>();

        dist = new Text("0m", 50, 50, -250, 50, false, 200, 75, Color.BLACK);
        coins = new Text("0$", 750, 50, 1325, 50, true, 200, 75, Color.BLACK);
        gems = new Text("0 Gems", 475-90, 50, 1050, 50, true, 280, 75, Color.BLACK);

        play = new Button("PLAY", 300,  (int) Game.height -800, 300, (int) Game.height +200, false, 400, 200, Color.BLACK) {
            @Override
            public boolean touch(float x, float y, Track track) {
                if (x < this.x || x > this.x + this.w || y < this.y || y > this.y + this.h) return false;
                dist.pos = true;
                track.pause = false;
                this.pos = false;
                return true;
            }
        };
        menu = new Button("End run", 350,  (int) (Game.height *0.7f) +75, 1050, (int) (Game.height *0.7f) +100, false, 300, 100, Color.BLACK) {
            @Override
            public boolean touch(float x, float y, Track track) {
                if (outside(x, y)) return false;
                track.pause = true;
                track.newGame();
                play.pos = true;
                dist.pos = false;
                File.save();
                return true;
            }
        };
        dist2 = new Text("0m", 300, (int) (Game.height *0.7f) -550, 300, -300, false, 400, 150, Color.BLACK);

        con = new Frame(225, (int) (Game.height *0.7f) -275, -650, (int) (Game.height *0.7f) -300, false, 550, 275, Color.BLACK);
        con.addElement(new Text("Continue playing?", 25, 25, 25, 25, true, 500, 75, Color.BLACK));
        con.addElement(new Button("Ad", 25, 175, 25, 175, true, 200, 75, Color.BLACK) {
            @Override
            public boolean touch(float x, float y, Track track) {
                if (outside(x, y)) return false;
                MainActivity.showRewardedAd();
                return true;
            }
        });
        con.addElement(new Button("20$", 325, 175, 325, 175, true, 200, 75, Color.BLACK) {
            @Override
            public boolean touch(float x, float y, Track track) {
                if (outside(x, y)) return false;
                if (File.getInt("coins") >= 20 && !track.revived) {
                    File.changeInt("coins", -20);
                    File.save();
                    track.secondChange();
                } else {
                    message("Not enough money!", 10);
                }
                return true;
            }
        });

        message = new Text("message", 100, 100, 100, -300, false, 800, 100, Color.BLACK);

        play.pos = true;

        elements.add(dist);
        elements.add(dist2);
        elements.add(coins);
        elements.add(gems);
        elements.add(menu);
        elements.add(play);
        elements.add(con);
        elements.add(message);
    }

    public void update(float time, Track track) {
        coins.setText(File.getInt("coins") +"$");
        gems.setText(File.getInt("gems") +" Gems");
        dist.setText(track.getDist() +"m");
        dist2.setText(track.getDist() +"m");

        menu.pos = track.gameOver != -1;
        dist2.pos = track.gameOver != -1;
        con.pos = track.gameOver != -1 && !track.revived;

        for (UIElement e : this.elements) {
            e.update(time);
        }

        msgTime -= time;
        if (msgTime <= 0) message.pos = false;
    }

    public void draw(Canvas canvas) {
        for (UIElement e : this.elements) {
            e.draw(canvas);
        }
    }

    public boolean touch(float x, float y, Track track) {
        boolean b = false;
        for (UIElement e : this.elements) {
            if (e.touch(x, y, track)) b = true;
        }
        return b;
    }

    public void message(String text, float length) {
        msgTime = length;
        message.setText(text);
        message.pos = true;
    }
}
