package com.example.yuanzhiwu.memorydemo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


/**
 * Created by yuanzhiwu on 18-7-16.
 */

public class LeakActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        intViews();
    }

    private void intViews() {
        TextView name = (TextView) findViewById(R.id.activityName);
        name.setText(this.getClass().getSimpleName());
    }
}
