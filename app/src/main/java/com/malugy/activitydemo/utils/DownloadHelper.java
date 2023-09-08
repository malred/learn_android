package com.malugy.activitydemo.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 1. download方法 url localPath listener
 * 2. listener: start, success fail progress
 * 3. 用async task封装
 */
public class DownloadHelper {
    public static void download(String url, String localPath, OnDownloadListener listener) {
        DownloadAsyncTask task = new DownloadAsyncTask(url, localPath, listener);
        task.execute();
    }

    public interface OnDownloadListener {
        void onStart();

        void onSuccess(int code, File f);

        void onFail(int code, File f, String msg);

        void onProgress(int progress);

        // 用抽象类实现接口,让实现类可以不实现不需要的方法
        abstract class SimpleDownloadListener implements OnDownloadListener {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(int progress) {

            }
        }
    }

    public static class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {
        String url;
        String filePath;
        OnDownloadListener listener;

        public DownloadAsyncTask(String url, String filePath, OnDownloadListener listener) {
            this.url = url;
            this.filePath = filePath;
            this.listener = listener;
        }

        /**
         * (主线程)在异步任务前执行
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 可操作ui
            if (listener != null) {
                listener.onStart();
            }
        }

        /**
         * (子进程)在另一个线程中处理事件
         *
         * @param strings 入参
         * @return 结果
         */
        @Override
        protected Boolean doInBackground(String... strings) {
            String apkUrl = url;

            try {
                URL url = new URL(apkUrl);
                // 构造链接并打开
                URLConnection conn = url.openConnection();
                InputStream input = conn.getInputStream();
                // 获得下载内容的总长度
                int contentLen = conn.getContentLength();

                // 下载地址处理
                File apkFile = new File(filePath);
                if (apkFile.exists()) {
                    boolean res = apkFile.delete();
                    if (!res) {
                        if (listener != null) {
                            listener.onFail(-1, apkFile, "文件删除失败");
                        }
                        return false;
                    }
                }

                // 已下载的大小
                int downloadSize = 0;

                byte[] bytes = new byte[1024];
                int len;

                // 创建输入管道
                OutputStream output = new FileOutputStream(filePath);
                // 一直下载
                while ((len = input.read(bytes)) != -1) {
                    output.write(bytes, 0, len);
                    downloadSize += len;

                    // 用long是因为数值太大int表示不了,甚至出现负数)
                    publishProgress((int) (Long.valueOf(downloadSize) * 100 / Long.valueOf(contentLen)));
                }

                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onFail(-2, new File(filePath), e.getMessage());
                }
                return false;
            }

            if (listener != null) {
                listener.onSuccess(0, new File(filePath));
            }
            return true;
        }

        /**
         * (主线程)异步任务执行结束后
         *
         * @param aBoolean
         */
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            // 可以进行结果处理
        }

        /**
         * (主进程)当进度改变
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // 收到进度,然后处理
            if (listener != null) {
                listener.onProgress(values[0]);
            }
        }
    }
}
