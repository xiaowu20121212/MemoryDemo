package com.example.yuanzhiwu.memorydemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import java.lang.ref.WeakReference;

public class HandlerLeakFixActivity extends LeakActivity {

    private Handler mHandler = new MyHandler(this);
    private static class MyHandler extends Handler {

        private WeakReference<Context> reference;
        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }
        @Override
        public void handleMessage(Message msg) {
            Context context = reference.get();
            if( context != null) {
                doHandleTheMessage(context, msg);
            }
        }
    };

    private static void doHandleTheMessage(Context context, Message msg) {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData(){
        //模拟数据处理和消息发送
        loadData(3);
    }

    private void loadData(int count){
        //...request
        for(int i=0;i<count;i++) {
            Message message = Message.obtain();
            if( i%3 == 0) {
                mHandler.sendMessage(message);
            } else {
                mHandler.sendMessageDelayed(message,200*1000);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_leak);
        loadData();
    }


    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
