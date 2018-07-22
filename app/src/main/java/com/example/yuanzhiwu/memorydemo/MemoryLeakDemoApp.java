package com.example.yuanzhiwu.memorydemo;

import android.os.Debug;

/**
 * Created by yuanzhiwu on 18-7-16.
 */

public class MemoryLeakDemoApp extends LeakCheckApp {
    @Override
    public void onCreate() {
        Debug.startMethodTracing("onCreate.trace");
        super.onCreate();
        StringBuilder stringBuilder = new StringBuilder();
       for (int i = 0;i < 10000; i++) {
           stringBuilder.append(String.valueOf(i));
       }
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
