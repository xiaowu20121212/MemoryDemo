package com.example.yuanzhiwu.memorydemo;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mSysTrace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSysTrace = findViewById(R.id.btn_sys_trace);
        mSysTrace.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (mSysTrace == v) {
            intent = new Intent(this, RecycleViewActivity.class);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
