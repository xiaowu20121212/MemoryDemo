package com.example.yuanzhiwu.memorydemo;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.DefaultLeakDirectoryProvider;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by yuanzhiwu on 18-7-16.
 */

public class LeakCheckApp extends Application {
    BlockCanary mBlockCanary;

    @Override
    public void onCreate() {
        super.onCreate();
        //startLeakCheck(true);
        //startStrictMode(true);
        Duration.setStart("installBlockCh");
        installBlockCh();
        Log.d("Duration",
                "installBlockCh called Time:"
                        + Duration.getDuration("installBlockCh"));
    }

    /**
     * 启动内存泄漏检测
     *
     * @param isStart
     */
    public void startLeakCheck(boolean isStart) {
        if (isStart) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
            LeakCanary.enableDisplayLeakActivity(this);
            LeakCanary.setDisplayLeakActivityDirectoryProvider(new DefaultLeakDirectoryProvider(this));
        }
    }

    public void startStrictMode(boolean devMode) {
        if (devMode) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()//线程策略（ThreadPolicy）
                    .detectDiskReads()//检测在UI线程读磁盘操作
                    .detectDiskWrites()//检测UI线程写磁盘操作
                    .detectCustomSlowCalls()//发现UI线程调用的哪些方法执行得比较慢
                    //.detectResourceMismatches()//最低版本为API23  发现资源不匹配
                    .detectNetwork() //检测在UI线程执行网络操作
                    .penaltyDialog()//一旦检测到弹出Dialog
                    //.penaltyDeath()//一旦检测到应用就会崩溃
                    .penaltyFlashScreen()//一旦检测到应用将闪屏退出 有的设备不支持
                    .penaltyDeathOnNetwork()//一旦检测到应用就会崩溃
                    .penaltyDropBox()//一旦检测到将信息存到DropBox文件夹中 data/system/dropbox
                    .penaltyLog()//一旦检测到将信息以LogCat的形式打印出来
                    .permitDiskReads()//允许UI线程在磁盘上读操作
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()//虚拟机策略（VmPolicy）
                    .detectActivityLeaks()//最低版本API11 用户检查 Activity 的内存泄露情况
                    //.detectCleartextNetwork()//最低版本为API23  检测明文的网络
                    //.detectFileUriExposure()//最低版本为API18   检测file://或者是content://
                    .detectLeakedClosableObjects()//最低版本API11  资源没有正确关闭时触发
                    .detectLeakedRegistrationObjects()//最低版本API16  BroadcastReceiver、ServiceConnection是否被释放
                    .detectLeakedSqlLiteObjects()//最低版本API9   资源没有正确关闭时回触发
                    .setClassInstanceLimit(OneInstanceClass.class, 2)//设置某个类的同时处于内存中的实例上限，可以协助检查内存泄露
                    .penaltyLog()//与上面的一致
                    //.penaltyDeath()
                    .build());

        } else {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .permitAll()
                    .build());
        }
    }

    private void installBlockCh() {
        mBlockCanary = BlockCanary.install(this, new AppBlockCanaryContext());
        mBlockCanary.stop();
    }

    boolean mIsBlockStart;
    public void startBlock() {
        if (mBlockCanary != null && !mIsBlockStart) {
            mBlockCanary.start();
            mIsBlockStart = true;
        }
    }
    public void stopBlock() {
        if (mBlockCanary != null && mIsBlockStart) {
            mBlockCanary.stop();
            mIsBlockStart = false;
        }
    }
}
