package com.example.yuanzhiwu.memorydemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;

import java.lang.ref.WeakReference;

public class ThreadLeakFixActivity extends LeakActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_leak);
        doSomeThing();
    }

    private Thread mThread;

    private void doSomeThing() {
        mThread = new Thread(new MyRunnable(this));
        mThread.start();
    }

    static class MyRunnable implements Runnable{
        WeakReference<Activity> wkActivity;

        MyRunnable(Activity activity) {
            wkActivity = new WeakReference<Activity>(activity);
        }
        @Override
        public void run() {
            SystemClock.sleep(200000);
            Activity activity = wkActivity.get();
            if( activity != null && !activity.isFinishing()) {
                doUseActivity(activity);
            }
        }
    }

    private static void doUseActivity(Activity activity) {
    }

    @Override
    protected void onDestroy() {
        try {
            mThread.join(); //等待现成执行完．这样会卡住主线程无法结束，并不是很好的办法
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
