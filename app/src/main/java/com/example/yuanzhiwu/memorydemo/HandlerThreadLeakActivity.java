package com.example.yuanzhiwu.memorydemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

public class HandlerThreadLeakActivity extends LeakActivity {

    private HandlerThread mHandlerThread;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread_leak);
        mHandlerThread = new HandlerThread("backgroud",
                Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mHandler = new MyHandler(mHandlerThread.getLooper());
        loadData();
    }

    //static 则不持有Activity, StrictMode检测不出来，实际Hander自身泄漏
    private static class MyHandler extends Handler {

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            doHandleTheMessage(msg);
        }
    }

    ;

    private static void doHandleTheMessage(Message msg) {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        //模拟数据处理和消息发送
        loadData(3);
    }

    private void loadData(int count) {
        //...request
        for (int i = 0; i < count; i++) {
            Message message = Message.obtain();
            if (i % 3 == 0) {
                mHandler.sendMessage(message);
            } else {
                mHandler.sendMessageDelayed(message, 200 * 1000);
            }
        }
    }


    @Override
    protected void onDestroy() {
        boolean fixed = false;
        if (fixed) {
            mHandler.removeCallbacksAndMessages(null);
            mHandlerThread.getLooper().quit();
        }
        super.onDestroy();
    }
}
