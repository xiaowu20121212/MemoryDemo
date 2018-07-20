package com.example.yuanzhiwu.memorydemo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.Trace;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends Activity {
    private RecyclerView mRecycleView;
    TestAdapter mAdapter;
    private EditText mInput;
    private List<String> mData = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_layout);
        mRecycleView = findViewById(R.id.recycle_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        for(int i = 0; i < 1000; i++) {
            mData.add(String.valueOf(i));
        }
        mAdapter = new TestAdapter();
        mRecycleView.setAdapter(mAdapter);
        mInput = findViewById(R.id.ed_input);
    }

    private class TestAdapter extends RecyclerView.Adapter<TestViewHolder> {


        @NonNull
        @Override
        public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null));
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
            Trace.beginSection("onBindViewHolder");
            holder.freshData(mData.get(position));
            Trace.endSection();
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    private class TestViewHolder extends RecyclerView.ViewHolder {

        public TestViewHolder(View itemView) {
            super(itemView);
        }
        void freshData(String s) {
            int j = 0;
            long startTime = SystemClock.uptimeMillis();
            String input = mInput.getText().toString();
            for(int i = 0; i < Math.random() * (TextUtils.isEmpty(input) ? 1: Integer.parseInt(input)) ; i++) {
                j = i;
            }
            long cost = SystemClock.uptimeMillis() - startTime;
            ((TextView) itemView.findViewById(R.id.tv_number)).setText(String.format("position: %s  random: %s  duration: %s", s, String.valueOf(j), String.valueOf(cost)));
            if (cost > 16) {
                StrictMode.noteSlowCall("slowCall cost=" + cost);
            }
        }
    }
}
