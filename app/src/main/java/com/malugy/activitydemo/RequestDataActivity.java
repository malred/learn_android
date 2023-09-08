package com.malugy.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.malugy.adapter.RequestDataAdapter;
import com.malugy.model.LessonInfo;
import com.malugy.model.LessonResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RequestDataActivity extends AppCompatActivity {
    public static final String STATUS = "status";
    public static final String MSG = "msg";
    public static final String DATA = "data";
    public static final String NAME = "name";
    private String req_url = "http://192.168.255.137:8888/teacher";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_data);

        listView = this.findViewById(R.id.main_list_view);

        // data item.view
        new RequestDataAsyncTask().execute();

        // 添加底部view
        LayoutInflater inflater =
                (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerV = inflater.inflate(R.layout.header_list_view, null);
        listView.addFooterView(headerV);
    }

    public class RequestDataAsyncTask extends AsyncTask<Void, Void, String> {

        private static final String TAG = "RequestDataAsyncTask";

        private String request(String reqUrl) {
            try {
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setConnectTimeout(30000);
                conn.setRequestMethod("GET");
                conn.connect();

                int rescode = conn.getResponseCode();

                if (rescode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader input_reader = new InputStreamReader(conn.getInputStream());
                    BufferedReader buf_reader = new BufferedReader(input_reader);
                    StringBuilder str_builder = new StringBuilder();

                    String line;

                    while ((line = buf_reader.readLine()) != null) {
                        str_builder.append(line);
                        Log.i(TAG, "request: "+line);
                    }
                    Log.i(TAG, "request: 111");
                    return str_builder.toString();
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // loading

        }

        @Override
        protected String doInBackground(Void... voids) {
            return request(req_url);
        }


        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            Log.i(TAG, "onPostExecute: " + res);
            // loading消失, 数据处理 刷新界面
            LessonResult lessonResult = new LessonResult();

            try {
                JSONObject jsonObject = new JSONObject(res);

                final int status = jsonObject.getInt(STATUS);
                lessonResult.setStatus(status);
                final String msg = jsonObject.getString(MSG);
                lessonResult.setMsg(msg);

                List<LessonInfo> lessonInfos = new ArrayList<>();
                JSONArray dataArr = jsonObject.getJSONArray(DATA);

                for (int i = 0; i < dataArr.length(); i++) {
                    LessonInfo info = new LessonInfo();
                    JSONObject temJsonObj = (JSONObject) dataArr.get(i);
                    info.setName(temJsonObj.getString(NAME));
                    lessonInfos.add(info);
                }
                lessonResult.setLessonInfoList(lessonInfos);
                // GSON | fastjson
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            listView.setAdapter(new RequestDataAdapter(
                    lessonResult.getLessonInfoList(), RequestDataActivity.this));
        }
    }

}

