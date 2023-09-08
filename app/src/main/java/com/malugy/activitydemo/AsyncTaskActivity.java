package com.malugy.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class AsyncTaskActivity extends AppCompatActivity {
    private static final String TAG = "AsyncTaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        new DownloadAsyncTask().execute("i feel so gooooooooood!!!!");
    }

    /* 泛型里填包装类型 */
    public class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {

        /**
         * (主线程)在异步任务前执行
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 可操作ui
        }

        /**
         * (子进程)在另一个线程中处理事件
         *
         * @param strings 入参
         * @return 结果
         */
        @Override
        protected Boolean doInBackground(String... strings) {
            for (int i = 0; i < 100; i++) {
                Log.i(TAG, "doInBackground:  " + strings[0]);
                // 抛出进度
                publishProgress(i);
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
        }

        // 下面两个用的不多
        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            //
        }
    }
}