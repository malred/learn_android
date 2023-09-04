package com.malugy.activitydemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentDemoActivity extends AppCompatActivity
        implements ListFragment.OnTitleClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_demo);

        // 设置点击事件
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 静态加载 fragment
                startActivity(new Intent(FragmentDemoActivity.this, StaticLoadFragmentActivity.class));
            }
        });

        // 1. xml里定义container 2. 创建fragment 3. fragment -> container
        ListFragment fragment = ListFragment.newInstance("list");
        // 设置点击回调
        fragment.setOnTitleClickListener(this);

        getSupportFragmentManager()
                // 开始将fragment放入container
                .beginTransaction()
                // 关联
                .add(R.id.list_container, fragment)
                 // 提交
                .commit();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.detail_container, ListFragment.newInstance("list"))
                .commit();
        // 移除
//        getSupportFragmentManager()
//                .beginTransaction()
//                .remove(fragment)
//                .commit();
        // 替换
        ListFragment fragment1 = ListFragment.newInstance("detail");
        fragment1.setOnTitleClickListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_container, fragment1)
                .commit();
    }

    @Override
    public void onClick(String title) {
        setTitle(title);
    }
}