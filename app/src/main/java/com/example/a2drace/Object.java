package com.example.a2drace;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Object {

    public float x, y;
    protected final float w, h;
    protected float dir;
    protected final Bitmap img;

    private final float limit;

    public Object(int x, int y, int w, int h, Bitmap image) {
        this.img = Bitmap.createScaledBitmap(image, w, h, false);
        this.limit = w * 0.85f;

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.dir = 0;
    }

    public void draw(Canvas canvas, float offsetX, float offsetY) {
        float x = (this.x + offsetX) * Game.scale;
        float y = (this.y + offsetY) * Game.scale;
        float w = this.w * Game.scale;
        float h = this.h * Game.scale;

        canvas.rotate(-dir, x, y);
        canvas.drawBitmap(img, (x - (w / 2f)), (y - (h * 0.7f)), new Paint());
        canvas.rotate(dir, x, y);
    }

    public float getDirInRadians() {
        return (float) (dir /360 *Math.PI *2);
    }

    public boolean collision(Object obj) {
        return obj.isInside(this.x, this.y, this.x -Math.sin(getDirInRadians())*this.h/2, this.y -Math.cos(getDirInRadians())*this.h/2);
    }

    public boolean collision(int wall) {
        double x2 = this.x -Math.sin(getDirInRadians())*this.h/2;
        return Math.abs(this.x - wall) < limit || Math.abs(x2 - wall) < limit;
    }

    public boolean isInside(double x, double y, double x2, double y2) {
        double x3 = this.x -Math.sin(getDirInRadians())*this.h/2;
        double y3 =this.y -Math.cos(getDirInRadians())*this.h/2;

        float d = distance(this.x - x, this.y - y);
        float d2 = distance(this.x - x2, this.y - y2);
        float d3 = distance(x3 - x, y3 - y);
        float d4 = distance(x3 - x2, y3 - y2);

        return d < limit || d2 < limit || d3 < limit || d4 < limit;
    }

    private float distance(double x, double y) {
        return (float) Math.sqrt(x*x + y*y);
    }
}
