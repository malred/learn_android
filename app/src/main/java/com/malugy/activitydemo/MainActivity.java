package com.malugy.activitydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void myclick(View v) {
        switch (v.getId()) {
            case R.id.button:
                startActivity(new Intent(MainActivity.this, TestActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(MainActivity.this, NewActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(MainActivity.this, DialogActivity.class));
                break;
            case R.id.button5:
                startActivity(new Intent(MainActivity.this, LifeCycleActivity.class));
                break;
            case R.id.button6:
                startActivity(new Intent(MainActivity.this, FragmentDemoActivity.class));
                break;
            case R.id.button7:
                startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
                break;
            case R.id.button8:
                startActivity(new Intent(MainActivity.this, NetworkActivity.class));
                break;
            case R.id.button9:
                startActivity(new Intent(MainActivity.this, HandlerActivity.class));
                break;
            case R.id.button10:
                startActivity(new Intent(MainActivity.this, DownloadActivity.class));
                break;
            case R.id.button11:
                startActivity(new Intent(MainActivity.this, CutdownTime.class));
                break;
            case R.id.button12:
                startActivity(new Intent(MainActivity.this, DiglettActivity.class));
                break;
            case R.id.button13:
                startActivity(new Intent(MainActivity.this, AsyncTaskActivity.class));
                break;
            case R.id.button14:
                startActivity(new Intent(MainActivity.this, AsyncDownloadActivity.class));
                break;
            case R.id.button15:
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                break;
        }
    }

    // 创建OptionMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载菜单资源
        getMenuInflater().inflate(R.menu.option, menu);
        // 让菜单显示
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // 获取被点击的item的id
        switch (item.getItemId()) {
            case R.id.save:
                Toast.makeText(this, "保存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                // 退出程序
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}