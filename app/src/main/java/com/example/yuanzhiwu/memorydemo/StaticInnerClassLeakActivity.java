package com.example.yuanzhiwu.memorydemo;

import android.os.Bundle;
import android.util.Log;


/**
 * 非静态内部类创建静态实例造成的内存泄漏
 * Activity内部类隐式持有Activity对象造成的泄漏
 */
public class StaticInnerClassLeakActivity extends LeakActivity {

    private static final String TAG = "Leak";

    private static InnerClassManager innerClassManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_instance_leak);
        innerClassManager = new InnerClassManager();
        innerClassManager.doSomeThing();
    }
    private class InnerClassManager {
        void doSomeThing() {
            Log.d(TAG, "doSomeThing: ");
        }
    }

    @Override
    protected void onDestroy() {
//        appManager = null;
        super.onDestroy();
    }
}
