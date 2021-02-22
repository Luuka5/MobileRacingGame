package com.example.a2drace;

import java.util.ArrayList;

public class Shop {
    public final ArrayList<Item> items;

    public Shop() {
        this.items = new ArrayList<>();
        this.items.add(new Item("Sport car", 1000, true, Game.coin));
    }
}
