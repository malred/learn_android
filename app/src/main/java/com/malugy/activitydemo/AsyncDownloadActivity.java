package com.malugy.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.malugy.activitydemo.utils.DownloadHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AsyncDownloadActivity extends AppCompatActivity {

    private static final String TAG = "AsyncDownloadActivity";
    private ProgressBar probar;
    private Button btn;
    private TextView tv;
    private static String down_url =
            "https://downloadr2.apkmirror.com/wp-content/uploads/2023/09/78/64f9c14aac22b/com.gamestar.perfectpiano_7.7.5-2100988_3arch_2dpi_14lang_2e877540aa2d3bb70bcbbc1e76f3aa1a_apkmirror.com.apkm?verify=1694090949-PYTtgkS5Q3G2esvOQBo6tRCDfxkb8DNH-LONy_shqNk";
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_download);

        initView();
        setListener();
        // 初始化UI数据
        setData();

    }

    private void setData() {
        probar.setProgress(0);
        btn.setText("点击下载");
        tv.setText("准备下载");
    }

    private void setListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 下载
                DownloadHelper.download(down_url, "", new DownloadHelper.OnDownloadListener.SimpleDownloadListener() {
                    @Override
                    public void onSuccess(int code, File f) {

                    }

                    @Override
                    public void onFail(int code, File f, String msg) {

                    }
                });
            }
        });
    }

    private void initView() {
        probar = findViewById(R.id.progressBar2);
        btn = findViewById(R.id.btn);
        tv = findViewById(R.id.down_txt);
    }


    /* 泛型里填包装类型 */
//    public class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {
//
//        /**
//         * (主线程)在异步任务前执行
//         */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // 可操作ui
//            btn.setText("下载中");
//            tv.setText("下载中");
//            probar.setProgress(0);
//        }
//
//        /**
//         * (子进程)在另一个线程中处理事件
//         *
//         * @param strings 入参
//         * @return 结果
//         */
//        @Override
//        protected Boolean doInBackground(String... strings) {
//            if (null != strings && strings.length > 0) {
//                String apkUrl = strings[0];
//
//                try {
//                    URL url = new URL(apkUrl);
//                    // 构造链接并打开
//                    URLConnection conn = url.openConnection();
//                    InputStream input = conn.getInputStream();
//                    // 获得下载内容的总长度
//                    int contentLen = conn.getContentLength();
//
//                    // 下载地址准备
////                    filePath = Environment.getExternalStorageDirectory() + File.separator + "imooc.apk";
//                    // android 10(Q)开始增加了沙盒机制，不能直接把文件保存到/sdcard目录下，
//                    // 只能保存到APP专属目录下；AndroidManifest.xml在标签下增加属性
//                    // 【android:requestLegacyExternalStorage=“true”】可以暂时保存到/sdcard路径下，
//                    // 但是Android11开始就失效了
//                    //
//                    //我们可以通过Context的getExternalFilesDir(null)方法获取APP专属目录
//                    filePath = getExternalFilesDir(null) + File.separator + "imooc.apk";
//                    // 下载地址处理
//                    File apkFile = new File(filePath);
//                    if (apkFile.exists()) {
//                        boolean res = apkFile.delete();
//                        if (!res) {
//                            return false;
//                        }
//                    }
//
//                    // 已下载的大小
//                    int downloadSize = 0;
//
//                    byte[] bytes = new byte[1024];
//                    int len;
//
//                    // 创建输入管道
//                    OutputStream output = new FileOutputStream(filePath);
//                    // 一直下载
//                    while ((len = input.read(bytes)) != -1) {
//                        output.write(bytes, 0, len);
//                        downloadSize += len;
//
//                        // 用long是因为数值太大int表示不了,甚至出现负数)
//                        publishProgress((int) (Long.valueOf(downloadSize) * 100 /Long.valueOf( contentLen)));
//                        Log.i(TAG, "len: " + len);
//                        Log.i(TAG, "downloadSize: " + downloadSize);
//                        Log.i(TAG, "contentLen: " + contentLen);
//                    }
//
//                    input.close();
//                    output.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            } else {
//                return false;
//            }
//
//            return true;
//        }
//
//        /**
//         * (主线程)异步任务执行结束后
//         *
//         * @param aBoolean
//         */
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            // 可以进行结果处理
//            btn.setText(aBoolean ? "下载完成" : "下载失败");
//            tv.setText(aBoolean ? "下载完成" + filePath : "下载失败");
//        }
//
//        /**
//         * (主进程)当进度改变
//         *
//         * @param values
//         */
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            // 收到进度,然后处理
//            if (values != null && values.length > 0) {
//                Log.i(TAG, "onProgressUpdate: true");
//                probar.setProgress(values[0]);
//                Log.i(TAG, "values: " + values[0]);
//            }
//        }
//    }
}