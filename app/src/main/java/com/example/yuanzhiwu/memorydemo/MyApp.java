package com.example.yuanzhiwu.memorydemo;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }
}
