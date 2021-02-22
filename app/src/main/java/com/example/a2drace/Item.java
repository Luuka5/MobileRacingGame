package com.example.a2drace;

import android.graphics.Bitmap;

public class Item {
    final Bitmap img;
    final String name;
    final int prize;
    final boolean currency;
    boolean purchased;

    public Item(String name, int prize, boolean currency, Bitmap img) {
        this.name = name;
        this.prize = prize;
        this.img = img;
        this.currency = currency;
    }

    public boolean buy(UI ui) {
        if (purchased) {
            ui.message("You have this skin already!", 10);
            return false;
        }

        if (getUnits() >= prize) {
            addUnits(prize);
            return true;
        }
        return false;
    }

    public void addUnits(int value) {
        if (currency) {
            File.changeInt("coins", value);
        } else {
            File.changeInt("gems", value);
        }
    }

    public int getUnits() {
        return currency ? File.getInt("coins") : File.getInt("gems");
    }

    public void savePurchase() {

    }
}
