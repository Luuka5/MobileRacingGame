package com.example.a2drace;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Frame extends UIElement {
    private ArrayList<UIElement> elements;

    public Frame(int x1, int y1, int x2, int y2, boolean pos, int w, int h, int color) {
        super(x1, y1, x2, y2, w, h, pos);
        this.elements = new ArrayList<>();
    }

    @Override
    public void drawContent(Canvas canvas) {
        for (UIElement e : this.elements) {
            e.offsetX = this.x;
            e.offsetY = this.y;
            e.draw(canvas);
        }
    }

    @Override
    public boolean touch(float x, float y, Track track) {
        boolean b = false;
        for (UIElement e : this.elements) {
            if (e.touch(x, y, track)) b = true;
        }
        return b;
    }

    public void addElement(UIElement e) {
        this.elements.add(e);
    }
}
