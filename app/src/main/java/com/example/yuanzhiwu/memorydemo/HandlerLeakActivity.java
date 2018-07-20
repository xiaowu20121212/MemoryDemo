package com.example.yuanzhiwu.memorydemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


/**
 * 泄漏Handler对象
 */
public class HandlerLeakActivity extends LeakActivity {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            doHandleTheMessage(msg);
        }
    };

    private static void doHandleTheMessage(Message msg) {
        try {
            Thread.sleep(200);
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

}
