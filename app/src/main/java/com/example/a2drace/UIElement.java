package com.example.a2drace;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class UIElement {
    protected float x, y, x1, y1, x2, y2;
    protected int w, h;
    protected boolean pos;

    public float offsetX = 0, offsetY = 0;

    private final Paint fill, stroke;

    protected UIElement(int x1, int y1, int x2, int y2, int w, int h, boolean pos) {
        this.w = w;
        this.h = h;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.pos = pos;
        this.x = getDesX();
        this.y = getDesY();

        this.fill = new Paint();
        this.fill.setColor(Color.WHITE);
        this.stroke = new Paint();
        this.stroke.setColor(Color.BLACK);
        this.stroke.setStyle(Paint.Style.STROKE);
        this.stroke.setStrokeWidth(10 * Game.scale);
    }

    public void update(float time) {
        this.x -= ((this.x - getDesX()) / 2) * time;
        this.y -= ((this.y - getDesY()) / 2) * time;
    }

    public void draw(Canvas canvas) {
        canvas.drawRoundRect(new RectF((int) ((offsetX+ x -20) *Game.scale), (int) ((offsetY+ y -20) *Game.scale), (int) ((offsetX+ x+w +20) *Game.scale), (int) ((offsetY+ y+h +20) *Game.scale)), 20, 20, this.fill);
        canvas.drawRoundRect(new RectF((int) ((offsetX+ x -20) *Game.scale), (int) ((offsetY+ y -20) *Game.scale), (int) ((offsetX+ x+w +20) *Game.scale), (int) ((offsetY+ y+h +20) *Game.scale)), 20, 20, this.stroke);
        drawContent(canvas);
    }

    protected abstract void drawContent(Canvas canvas);
    public abstract boolean touch(float x, float y, Track track);

    private float getDesX() {
        return pos ? x1 : x2;
    }

    private float getDesY() {
        return pos ? y1 : y2;
    }

    protected boolean outside(float x, float y) {
        return  x < offsetX+ this.x || x > offsetX+ this.x + this.w || y < offsetY+ this.y || y > offsetY+ this.y + this.h;
    }
}
