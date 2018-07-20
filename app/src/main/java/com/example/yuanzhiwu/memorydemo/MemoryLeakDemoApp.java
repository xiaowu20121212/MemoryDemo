package com.example.yuanzhiwu.memorydemo;

/**
 * Created by yuanzhiwu on 18-7-16.
 */

public class MemoryLeakDemoApp extends LeakCheckApp {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
