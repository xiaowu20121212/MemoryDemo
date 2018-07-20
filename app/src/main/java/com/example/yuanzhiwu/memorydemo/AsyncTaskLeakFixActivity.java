package com.example.yuanzhiwu.memorydemo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;


import java.lang.ref.WeakReference;

public class AsyncTaskLeakFixActivity extends LeakActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        doSomeThing();
    }

    private MyAsyncTask mTask;

    private void doSomeThing() {
        mTask = new MyAsyncTask(this);
        mTask.execute();
    }

    static class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Context> weakReference;
        public MyAsyncTask(Context context) {
            weakReference = new WeakReference<>(context);
        }
        @Override
        protected Void doInBackground(Void... params) {
            SystemClock.sleep(10000);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AsyncTaskLeakFixActivity activity = (AsyncTaskLeakFixActivity) weakReference.get();
            if (activity != null) {
                //...
            }
        }
    }

    @Override
    protected void onDestroy() {
        mTask.cancel(true);
        super.onDestroy();
    }
}
