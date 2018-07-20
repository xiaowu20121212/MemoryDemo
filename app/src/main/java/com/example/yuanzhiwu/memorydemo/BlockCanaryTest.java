package com.example.yuanzhiwu.memorydemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BlockCanaryTest extends Activity implements View.OnClickListener{
    private Button mStart, mStop, mRunning;
    private EditText mInput;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_canary_layout);
        mStart = findViewById(R.id.btn_start);
        mStop = findViewById(R.id.btn_stop);
        mRunning = findViewById(R.id.btn_run);
        mInput = findViewById(R.id.ed_time);
        mStart.setOnClickListener(this);
        mStop.setOnClickListener(this);
        mRunning.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mStart) {
            ((LeakCheckApp) getApplicationContext()).startBlock();
            Toast.makeText(this, "BlockCanary Start;", Toast.LENGTH_SHORT).show();
        } else if (v == mStop) {
            ((LeakCheckApp) getApplicationContext()).stopBlock();
            Toast.makeText(this, "BlockCanary Stop;", Toast.LENGTH_SHORT).show();
        } else if (v == mRunning) {
            if (!TextUtils.isEmpty(mInput.getText().toString())) {
                Toast.makeText(this, "Test...;", Toast.LENGTH_SHORT).show();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(Long.parseLong(mInput.getText().toString()));
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((LeakCheckApp) getApplicationContext()).stopBlock();
    }
}
