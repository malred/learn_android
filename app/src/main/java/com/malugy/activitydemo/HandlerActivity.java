package com.malugy.activitydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class HandlerActivity extends AppCompatActivity {

    private static final String TAG = "HandlerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        TextView textView = findViewById(R.id.hi_txt);

        // 这里都是主线程在操作
        // 创建handler
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                // 处理消息
                Log.i(TAG, "handleMessage: " + msg.what);
                if (msg.what == 1001) {
                    // 这里是主线程,进行消息处理
                    textView.setText("test");
                    Log.d(TAG,"handleMessage: "+msg.arg1 );
                }
            }
        };

        findViewById(R.id.handle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                textView.setText("origin");  // ok
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 也可能做耗时操作,所以开线程
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        textView.setText("origin"); // error: 主线程才能更新
                        // 通知UI更新
                        handler.sendEmptyMessage(1001); // 通过handler事件来处理

                        Message message = Message.obtain();
                        message.what = 1002;
                        message.arg1 = 1003;
                        message.arg2 = 1004;
                        message.obj = HandlerActivity.this;

                        handler.sendMessage(message);
                        // 3秒后发送
                        handler.sendMessageAtTime(message, SystemClock.uptimeMillis()+3000);
                        // 延迟两秒发送
                        handler.sendMessageDelayed(message,2000);

                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                int a = 1 + 2 + 3;
                            }
                        };
                        handler.post(r);
                        r.run();

                        handler.postDelayed(r,2000);
                    }
                }).start();
            }
        });

        // 没有内容的消息,但是有编号(1001)
//        handler.sendEmptyMessage(1001);
    }
}