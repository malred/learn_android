package com.malugy.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.malugy.activitydemo.entity.jsondata;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class NetworkActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button button;
    private static final String TAG = "NetworkActivity";
    private String result;
    private Button parseDataButton;
    private String get_url = "https://api.wrdan.com/hitokoto";
    private String post_url = "https://api.wrdan.com/hitokoto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        findViews();
        setListeners();
    }

    private void setListeners() {
        button.setOnClickListener(this);
        parseDataButton.setOnClickListener(this);
    }

    private void findViews() {
        textView = findViewById(R.id.res_txt);
        button = findViewById(R.id.get_btn);
        parseDataButton = findViewById(R.id.parse_btn);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_btn:
                // 不能在主线程进行网络请求
                // 因为大部分带界面交互的应用，都是通过一个死循环的事件等待用户输入（触屏等）
                // 然后进行即时事件处理，如果这个时候进行耗时操作，就很容易卡死
                // 而网络请求就是耗时操作
                // 当事件处理完成，就通知主进程更新UI
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestDataByGet();
                    }
                }).start();
                break;
            case R.id.parse_btn:
                handleJsonData(result);
                break;
        }
    }

    private void handleJsonData(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);

            int ret = jsonObject.getInt("ret");
            String text = jsonObject.getString("text");
            String source = jsonObject.getString("source");
            String author = jsonObject.getString("author");

            jsondata jsondata = new jsondata(ret, text, source, author);

            textView.setText(jsondata.toString());
        } catch (Exception e) {

        }
    }

    private void requestDataByGet() {
        try {
            URL url = new URL(get_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30 * 1000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.connect(); // 发起连接

            int resCode = connection.getResponseCode();
            String resMsg = connection.getResponseMessage();

            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                result = streamToString(inputStream);

                // 只有主线程可以修改ui
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(result);
                    }
                });
            } else {
                // todo: error fail
                Log.e(TAG, "run: error code:" + resCode + ", message:" + resMsg);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void requestDataByPost() {
        try {
            URL url = new URL(post_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30 * 1000);
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Accept-Charset", "UTF-8");

            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.setUseCaches(false);
            connection.connect(); // 发起连接

            String data = "username=" + getEncodeVal("xxx") + "&number=111111111";

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            int resCode = connection.getResponseCode();
            String resMsg = connection.getResponseMessage();

            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                result = streamToString(inputStream);

                // 只有主线程可以修改ui
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(result);
                    }
                });
            } else {
                // todo: error fail
                Log.e(TAG, "run: error code:" + resCode + ", message:" + resMsg);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getEncodeVal(String x) {
        try {
            // utf-8编码，防止特殊字符
            return URLEncoder.encode(x, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Unicode字符转换为UTF-8类型字符串
     */
    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuilder retBuf = new StringBuilder();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else {
                    retBuf.append(unicodeStr.charAt(i));
                }
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return 字符串
     */
    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }
}