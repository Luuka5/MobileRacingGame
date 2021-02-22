package com.example.a2drace;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;


public class File {
    public static Context context;
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    private static HashMap<String, Integer> ints = new HashMap<>();

    public static void setContext(Context c) {
        context = c;
        pref = context.getSharedPreferences("AppData", 0);
        editor = pref.edit();
    }

    public static void save() {
        editor.clear();
        for (String s : ints.keySet()) {
            editor.putInt(s, ints.get(s));
        }
        editor.commit();
    }

    public static void load(String[] keys) {
        ints = new HashMap<>();
        for (String s : keys) {
            ints.put(s, pref.getInt(s, 0));
        }
    }

    public static int getInt(String key) {
        return ints.get(key);
    }

    public static int setInt(String key, int value) {
        return ints.put(key, value);
    }

    public static int changeInt(String key, int value) {
        return ints.put(key, ints.get(key) + value);
    }
}
