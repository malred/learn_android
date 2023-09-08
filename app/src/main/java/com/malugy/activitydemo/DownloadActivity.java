package com.malugy.activitydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadActivity extends AppCompatActivity {

    public static final int DOWNLOAD_MSG_CODE = 100001;
    private static final int DOWNLOAD_MSG_FAIL_CODE = 100002;
    public static final String DOWNLOAD_URL = "http://120.55.156.38/107904638/1693973615/develope-q8.lanzouc.com";
    private static final String TAG = "DownloadActivity";
    private Handler handler;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        ProgressBar progressBar = findViewById(R.id.progressbar);

        // 主线程 -> 点击按钮 | 发起下载 | 开启子线程下载 | 下载过程中通知主线程 | 主线程更新进度条
        findViewById(R.id.down_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        verifyStoragePermissions(DownloadActivity.this);
                        download(DOWNLOAD_URL);
                    }
                }).start();
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case DOWNLOAD_MSG_CODE:
                        progressBar.setProgress((Integer) msg.obj);
                        break;
                    case DOWNLOAD_MSG_FAIL_CODE:

                        break;
                }
            }
        };
    }

    private void download(String url) {
        try {
            URL download_url = new URL(url);
            URLConnection conn = download_url.openConnection();

            InputStream inputStream = conn.getInputStream();

            // 获取文件的总长度
            int contentLen = conn.getContentLength();

            String downloadFolderName =
//                    Environment.getExternalStorageDirectory()
                    getApplicationContext().getFilesDir().getAbsolutePath()
                            + File.separator + "malred" + File.separator;

            File file = new File(downloadFolderName);
            if (!file.exists()) {
                file.mkdir();
            }

            String filename = downloadFolderName + "hello.zip";

            File tar_file = new File(filename);

            if (tar_file.exists()) {
                tar_file.delete();
            }

            int downloadSize = 0;
            byte[] bytes = new byte[1024];

            int length = 0;

            OutputStream outputStream = new FileOutputStream(tar_file);
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
                downloadSize += length;
                // update UI
                Message message = Message.obtain();
                message.obj = downloadSize * 100 / contentLen;
                message.what = DOWNLOAD_MSG_CODE;
                handler.sendMessage(message);
            }

            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            Message message = Message.obtain();
            message.what = DOWNLOAD_MSG_FAIL_CODE;
            handler.sendMessage(message);
            throw new RuntimeException(e);
        }
    }
}