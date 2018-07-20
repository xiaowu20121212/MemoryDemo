package com.example.yuanzhiwu.memorydemo;

import com.github.moduth.blockcanary.BlockCanaryContext;

public class AppBlockCanaryContext extends BlockCanaryContext {
    // override to provide context like app qualifier, uid,     network type, block threshold, log save path
    // this is default block threshold, you can set it by phone's performance
    @Override
    public int getConfigBlockThreshold() {
        return 500;
    }
    // if set true, notification will be shown, else only write log file
    @Override
    public boolean isNeedDisplay() {
        return true;
    }
    // path to save log file (在SD卡目录下)
    @Override
    public String getLogPath() {
        return "/blockcanary/performance";
    }
}