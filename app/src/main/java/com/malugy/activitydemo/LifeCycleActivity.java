package com.malugy.activitydemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

public class LifeCycleActivity extends AppCompatActivity {
    // 快捷: logi
    private static final String TAG = "LifeCycleActivity";
    public static final String BUTTON_TITLE = "button_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);
//        String val =   savedInstanceState.getString("key","");
//        Log.i(TAG, val);
        Log.i(TAG, "onCreate: ");
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    // 此时可以和用户进行交互
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    // 被暂停
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    // 按下返回键

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "onBackPressed: ");
    }

    // 应用被销毁前 
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("key", "hello");
        Log.i(TAG, "onSaveInstanceState: ");
    }

    private void initViews() {
        findViewById(R.id.buttonActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(LifeCycleActivity.this, TestActivity.class);
                //===============第一种=================================
                //intent.putExtra(BUTTON_TITLE,getString(R.string.imooc_title));
                //startActivity(intent);

                //==============第二种=================================
                Bundle bundle = new Bundle();
                bundle.putString(BUTTON_TITLE, "慕课网");
                intent.putExtra(BUTTON_TITLE, bundle);
//                startActivity(intent);
                startActivityForResult(intent, 999);

                //==============第三种=================================
                //intent.putExtra(BUTTON_TITLE, new User());
                //startActivity(intent);
            }
        });

        findViewById(R.id.buttonDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(LifeCycleActivity.this)
                        .setTitle("你好")
                        .setMessage("我帅吗？")
                        .setNegativeButton("你好帅的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("呵呵哒", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();
            }
        });

        findViewById(R.id.buttonDialogActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LifeCycleActivity.this, DialogShowActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            setTitle("返回");
        }
    }

}