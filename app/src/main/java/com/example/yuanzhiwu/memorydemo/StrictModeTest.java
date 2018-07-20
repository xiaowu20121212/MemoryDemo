package com.example.yuanzhiwu.memorydemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Permission;
import java.util.ArrayList;

public class StrictModeTest extends Activity implements View.OnClickListener {
    private Button mStart, mStop;
    private Button mNetWork, mWrite, mSlowMethod, mLeak, mClosable, mInstance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.strict_mode_layout);
        mStart =findViewById(R.id.btn_start);
        mStart.setOnClickListener(this);
        mStop =findViewById(R.id.btn_stop);
        mStop.setOnClickListener(this);
        mNetWork =findViewById(R.id.btn_network);
        mNetWork.setOnClickListener(this);
        mWrite =findViewById(R.id.btn_write);
        mWrite.setOnClickListener(this);
        mSlowMethod =findViewById(R.id.btn_slow);
        mSlowMethod.setOnClickListener(this);
        mLeak =findViewById(R.id.btn_leak);
        mLeak.setOnClickListener(this);
        mClosable =findViewById(R.id.btn_closable);
        mClosable.setOnClickListener(this);
        mInstance =findViewById(R.id.btn_instance);
        mInstance.setOnClickListener(this);
    }

    /**
     * 网络连接的操作
     */
    private void testNetwork() {
       new Thread(){
           @Override
           public void run() {
               super.run();
               StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().penaltyLog().build());
               try {
                   URL url = new URL("http://www.baidu.com");
                   HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                   conn.connect();
                   BufferedReader reader = new BufferedReader(new InputStreamReader(
                           conn.getInputStream()));
                   String lines = null;
                   StringBuffer sb = new StringBuffer();
                   while ((lines = reader.readLine()) != null) {
                       sb.append(lines);
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       }.start();
    }

    /**
     * 文件系统的操作
     */
    public void writeToExternalStorage() {
        int checkSelfPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            return;
        }
        File externalStorage = Environment.getExternalStorageDirectory();
        File mbFile = new File(externalStorage, "strict.txt");
        try {
            if (!mbFile.exists()) {
                mbFile.createNewFile();
            }
            OutputStream output = new FileOutputStream(mbFile, true);
            output.write("yuanzhiwu test strict mode".getBytes());
            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void executeTask(Runnable task) {
        long SLOW_CALL_THRESHOLD = 500;
        long startTime = SystemClock.uptimeMillis();
        task.run();
        long cost = SystemClock.uptimeMillis() - startTime;
        if (cost > SLOW_CALL_THRESHOLD) {
            StrictMode.noteSlowCall("slowCall cost=" + cost);
        }
    }

    private void testActivityLeak() {
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(20000);
            }
        }.start();
    }

    private void testClosable() {
        int checkSelfPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            return;
        }
        File newxmlfile = new File(Environment.getExternalStorageDirectory(), "strict_not_close.txt");
        try {
            if (!newxmlfile.exists()) {
                newxmlfile.createNewFile();
            }
            FileWriter fw = new FileWriter(newxmlfile);
            fw.write("yuanzhiwu test closeble");
            //fw.close(); 特意没有关闭
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<OneInstanceClass> list = new ArrayList<>();

    private void testInstance() {
        list.add(new OneInstanceClass());
        list.add(new OneInstanceClass());
        list.add(new OneInstanceClass());
        list.add(new OneInstanceClass());
        list.add(new OneInstanceClass());
    }

    @Override
    public void onClick(View v) {
        if (v == mNetWork) {
            testNetwork();
        } else if (v == mWrite) {
            writeToExternalStorage();
        } else if (v == mSlowMethod) {
            executeTask(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(2000);
                }
            });
        } else if (v == mLeak) {
            testActivityLeak();
        }else if (v == mClosable) {
            testClosable();
        } else if (v == mInstance) {
            testInstance();
        } else if (v == mStop) {
            ((LeakCheckApp) getApplicationContext()).startStrictMode(false);
        } else if (v == mStart) {
            ((LeakCheckApp) getApplicationContext()).startStrictMode(true);

        }
    }
}
