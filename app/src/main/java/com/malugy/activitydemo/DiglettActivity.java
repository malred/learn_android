package com.malugy.activitydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

public class DiglettActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    public static final int CODE = 10086;
    private static int RANDOM_NUM;
    private TextView resTxtView;
    private ImageView diglettImgV;
    private Button startBtn;

    public int width;
    public int height;

//    public int[][] positions = new int[][]{
//            {342, 180}, {432, 880},
//            {521, 256}, {429, 780},
//            {456, 976}, {145, 665},
//            {123, 678}, {564, 567},
//    };

    private int totalCount;
    private int successCount;

    public static final int MAX_COUNT = 10;

    private DiglettHandler handler = new DiglettHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diglett);

        resTxtView = findViewById(R.id.res_txt);
        diglettImgV = findViewById(R.id.img_view);
        startBtn = findViewById(R.id.start_btn);

        startBtn.setOnClickListener(this);
        diglettImgV.setOnTouchListener(this);

        setTitle("打地鼠");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn:
                start();
                break;
        }
    }

    public void start() {
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        width = display.getWidth();
        height = display.getHeight();

        resTxtView.setText("game开始");
        startBtn.setText("游戏中......");
        startBtn.setEnabled(false);

        // 发送消息 handler.sendMessage
        next(0);
    }

    // 随机出现下一个地鼠
    public void next(int delayTime) {

//        int position = new Random().nextInt(positions[0].length);

        Message msg = Message.obtain();
        msg.what = CODE;
//        msg.arg1 = position;
        msg.arg1 = new Random().nextInt(width);
        msg.arg2 = new Random().nextInt(height);

        handler.sendMessageDelayed(msg, delayTime);
        totalCount++;
    }

    public void clear() {
        totalCount = 0;
        successCount = 0;
        diglettImgV.setVisibility(View.GONE);
        startBtn.setText("点击开始");
        startBtn.setEnabled(true);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.setVisibility(View.GONE);
        successCount++;
        resTxtView.setText("打到了" + successCount + "只, 共" + MAX_COUNT + "只.");
        return false;
    }

    public static class DiglettHandler extends Handler {
        public final WeakReference<DiglettActivity> weakReference;

        public DiglettHandler(DiglettActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            DiglettActivity activity = weakReference.get();

            switch (msg.what) {
                case CODE:
                    if (activity.totalCount > MAX_COUNT) {
                        activity.clear();
                        Toast.makeText(activity, "地鼠打完了!", Toast.LENGTH_LONG).show();
                        return;
                    }

//                    int position = msg.arg1;
                    int positionx = msg.arg1;
                    int positiony = msg.arg2;
                    // 设置地鼠出现位置
//                    activity.diglettImgV.setX(activity.positions[position][0]);
//                    activity.diglettImgV.setY(activity.positions[0][position]);
                    activity.diglettImgV.setX(positionx);
                    activity.diglettImgV.setY(positiony);
                    activity.diglettImgV.setVisibility(View.VISIBLE);

                    // 随机0~500(不包括500)的随机数
                    RANDOM_NUM = 500;
                    int rand_time = new Random().nextInt(RANDOM_NUM) + RANDOM_NUM;

                    activity.next(rand_time);
                    break;
            }
        }
    }
}