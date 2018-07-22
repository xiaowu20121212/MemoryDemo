package com.example.yuanzhiwu.memorydemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;


public class ThreadLeakActivity extends LeakActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_leak);
        doSomeThing();
    }
    private void doSomeThing() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(200000);
                Activity activity = ThreadLeakActivity.this;
                doUseActivity(activity);
            }
        }).start();
    }

    private static void doUseActivity(Activity activity) {
    }
}
