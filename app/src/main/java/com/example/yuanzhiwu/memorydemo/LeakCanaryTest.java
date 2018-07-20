package com.example.yuanzhiwu.memorydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LeakCanaryTest extends Activity implements View.OnClickListener {
    private Button mStart, mStop;
    private Button mSingleTon, mAsyncTask, mHandler, mHandlerThread, mStaticClass, mAsyncClass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leak_canary_layout);
        mStart =findViewById(R.id.btn_start);
        mStart.setOnClickListener(this);
        mStop =findViewById(R.id.btn_stop);
        mStop.setOnClickListener(this);
        mSingleTon =findViewById(R.id.btn_single_ton);
        mSingleTon.setOnClickListener(this);
        mAsyncTask =findViewById(R.id.btn_async_task);
        mAsyncTask.setOnClickListener(this);
        mHandler =findViewById(R.id.btn_handler);
        mHandler.setOnClickListener(this);
        mHandlerThread =findViewById(R.id.btn_handler_thread);
        mHandlerThread.setOnClickListener(this);
        mStaticClass =findViewById(R.id.btn_static_class);
        mStaticClass.setOnClickListener(this);
        mAsyncClass =findViewById(R.id.btn_async_class);
        mAsyncClass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v == mSingleTon) {
            intent = new Intent(this, SingletonLeakActivity.class);
        }else if (v == mAsyncTask) {
            intent = new Intent(this, AsyncTaskLeakActivity.class);
        } else if (v == mHandler) {
            intent = new Intent(this, HandlerLeakActivity.class);
        } else if (v == mHandlerThread) {
            intent = new Intent(this, HandlerThreadLeakActivity.class);
        } else if (v == mStaticClass) {
            intent = new Intent(this, StaticInnerClassLeakActivity.class);
        }else if (mAsyncClass == v) {
            intent = new Intent(this, ThreadLeakActivity.class);
        } else if (v == mStart) {
            ((LeakCheckApp) getApplicationContext()).startLeakCheck(true);
            Toast.makeText(this, "LeakCanary start", Toast.LENGTH_SHORT).show();
        } else if (v == mStop) {

        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
