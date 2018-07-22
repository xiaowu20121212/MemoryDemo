package com.example.yuanzhiwu.memorydemo;

import android.content.Context;
import android.graphics.Canvas;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.Trace;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class DrawTextView extends TextView {
    private int mNumber;
    public DrawTextView(Context context) {
        super(context);
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Trace.beginSection("DrawTextView");
        long startTime = SystemClock.uptimeMillis();
        for(int i = 0; i < mNumber ; i++) {
        }
        long cost = SystemClock.uptimeMillis() - startTime;
        if (cost > 16) {
            StrictMode.noteSlowCall("slowCall cost=" + cost);
        }
        Trace.endSection();
    }

    public void setNumber(int number) {
        this.mNumber = number;
    }
}
