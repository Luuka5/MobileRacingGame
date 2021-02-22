package com.example.a2drace;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Button extends UIElement {
    private String text;
    private Paint col;

    public Button(String text, int x1, int y1, int x2, int y2, boolean pos, int w, int h, int color) {
        super(x1, y1, x2, y2, w, h, pos);
        this.text = text;

        col = new Paint();
        col.setColor(color);
        col.setTextSize(h * 0.8f);
    }

    @Override
    public void drawContent(Canvas canvas) {
        canvas.drawText(text, (offsetX + x+10) *Game.scale, (offsetY+ y+h*0.75f) *Game.scale, this.col);
    }

    public void setColor(int color) {
        col = new Paint();
        col.setColor(color);
        col.setTextSize(h * 0.8f);
    }
}
