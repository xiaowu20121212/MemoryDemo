package com.example.yuanzhiwu.memorydemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;


public class AsyncTaskLeakActivity extends LeakActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        doSomeThing();
    }


    private void doSomeThing() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(10000);
                return null;
            }
        }.execute();
    }
}
