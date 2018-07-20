package com.example.yuanzhiwu.memorydemo;

import android.content.Context;

/**
 * Created by yuanzhiwu on 18-7-16.
 */

public class AppManager {
    private static AppManager instance;
    private Context context;
    private AppManager(Context context) {
        this.context = context;
    }
    public static AppManager getInstance(Context context) {
        if (instance == null) {
            instance = new AppManager(context);
        }
        return instance;
    }
}