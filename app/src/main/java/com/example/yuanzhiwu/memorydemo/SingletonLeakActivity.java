package com.example.yuanzhiwu.memorydemo;

import android.os.Bundle;


/**
 * 单例持有Activity的Context造成的泄漏
 */
public class SingletonLeakActivity extends LeakActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleton_leak);
        AppManager appManager = AppManager.getInstance(this);
    }

}
