package com.example.a2drace;

public class Coin extends Object {
    public final boolean gem;

    public Coin(int x, int y, int w, int h, boolean gem) {
        super(x, y, w, h, gem ? Game.gem : Game.coin);
        this.gem = gem;
    }
}
