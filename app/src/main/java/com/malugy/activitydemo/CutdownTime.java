package com.malugy.activitydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class CutdownTime extends AppCompatActivity {

    public static final int COUNTDOWN_TIME_CODE = 100001;
    public static final int DELAY_MILLIS = 1000; // 倒计时间隔
    public static final int MAX_COUNT = 10; // 倒计时起始
    public static final int MIN_COUNT = 0; // 倒计时结束
    private TextView cutdown_time; // 显示文本的view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutdown_time);

        cutdown_time = findViewById(R.id.cutdown_txt);

        // handler是异步的,持有activity的引用(就算该activity被销毁),
        // handler不会被GC回收,可能导致内存泄漏
        CutdownTimeHandler handler = new CutdownTimeHandler(this);

        Message msg = Message.obtain();
        msg.what = COUNTDOWN_TIME_CODE;
        msg.arg1 = MAX_COUNT;

        handler.sendMessageDelayed(msg, DELAY_MILLIS);
    }

    // 静态handler
    public static class CutdownTimeHandler extends Handler {
        // 弱引用
        final WeakReference<CutdownTime> weakReference;

        public CutdownTimeHandler(CutdownTime cutdownTime) {
            this.weakReference = new WeakReference<>(cutdownTime);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            CutdownTime activity = weakReference.get();

            switch (msg.what) {
                case COUNTDOWN_TIME_CODE:
                    int val = msg.arg1;

                    // 再次发送msg
                    if (val > MIN_COUNT) {
                        activity.cutdown_time.setText(String.valueOf(val - 1));
                        Message new_msg = Message.obtain();
                        new_msg.what = COUNTDOWN_TIME_CODE;
                        new_msg.arg1 = val - 1;
                        sendMessageDelayed(new_msg, DELAY_MILLIS);
                    }
                    break;
            }
        }
    }
}