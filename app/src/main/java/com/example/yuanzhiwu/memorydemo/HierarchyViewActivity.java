package com.example.yuanzhiwu.memorydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.debug.hv.ViewServer;

/**
 * Step 1. Add the JitPack repository to your build file
 Add it in your root build.gradle at the end of repositories:

 allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
 }
 Step 2. Add the dependency
 dependencies {
    implementation 'com.github.romainguy:ViewServer:-SNAPSHOT'
 }
 */
public class HierarchyViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hierarchy_view_layout);
        ViewServer.get(this).addWindow(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }
}
