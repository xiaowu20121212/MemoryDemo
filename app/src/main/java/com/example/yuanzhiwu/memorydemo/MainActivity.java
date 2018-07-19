package com.example.yuanzhiwu.memorydemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private RecyclerView mRecycleView;

    private List<Bean> mData  = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mRecycleView = findViewById(R.id.recycle_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(new MainAdapter());
    }

    private void init() {
        mData.add(new Bean("SysTrace Text", RecycleViewActivity.class));
        mData.add(new Bean("Hierrarchy View Test", HierarchyViewActivity.class));
    }

    private class MainAdapter extends RecyclerView.Adapter<MainActivity.MainViewHolder>{

        @NonNull
        @Override
        public MainActivity.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MainActivity.MainViewHolder(new Button(MainActivity.this));
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onBindViewHolder(@NonNull MainActivity.MainViewHolder holder, int position) {
            holder.freshData(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

    }

    private class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public MainViewHolder(View itemView) {
            super(itemView);
        }
        void freshData(Bean bean) {
            ((Button)this.itemView).setText(bean.mText);
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, mData.get(getAdapterPosition()).mClass);
            startActivity(intent);
        }
    }

    private static class Bean {
        private String mText;
        private Class mClass;

        public Bean(String text, Class cls) {
            this.mText = text;
            this.mClass = cls;
        }
    }
}
