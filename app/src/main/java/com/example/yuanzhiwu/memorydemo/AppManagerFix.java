package com.example.yuanzhiwu.memorydemo;

import android.content.Context;

/**
 *Created by yuanzhiwu on 18-7-16.
 */

public class AppManagerFix {
    private static AppManagerFix instance;
    private Context context;

    private AppManagerFix(Context context) {
        this.context = context.getApplicationContext();
    }
    public static AppManagerFix getInstance(Context context) {
        if (instance == null) {
            instance = new AppManagerFix(context);
        }
        return instance;
    }
}