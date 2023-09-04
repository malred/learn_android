package com.malugy.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
    }
    public void myclick(View v) {
        switch (v.getId()) {
            case R.id.button9:
                startActivity(new Intent(ViewPagerActivity.this, ImageViewPagerAdapter.class));
                break;
            case R.id.button10:
                startActivity(new Intent(ViewPagerActivity.this, TabViewPagerActivity.class));
                break;
            case R.id.button11:
                startActivity(new Intent(ViewPagerActivity.this, BottomTabActivity.class));
                break;
        }
    }
}