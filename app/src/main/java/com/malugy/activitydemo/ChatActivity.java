package com.malugy.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = findViewById(R.id.chat_list_view);

        // 准备数据
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(
                1, 2, "刘小明", "8:20", "你好", true));
        chatMessages.add(new ChatMessage(
                2, 1, "小军", "8:21", "我好", false));
        chatMessages.add(new ChatMessage(
                1, 2, "刘小明", "8:22", "今天天气怎么样", true));
        chatMessages.add(new ChatMessage(
                2, 1, "小军", "8:23", "热死啦", false));

        listView.setAdapter();
    }

    public class ChatMessage {
        private int id;
        private int friend_id;
        private String name;
        private String date;
        private String content;
        private boolean isComeMsg; // 是不是对方发来的消息

        public ChatMessage(int id, int friend_id, String name, String date, String content, boolean isComeMsg) {
            this.id = id;
            this.friend_id = friend_id;
            this.name = name;
            this.date = date;
            this.content = content;
            this.isComeMsg = isComeMsg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFriend_id() {
            return friend_id;
        }

        public void setFriend_id(int friend_id) {
            this.friend_id = friend_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isComeMsg() {
            return isComeMsg;
        }

        public void setComeMsg(boolean comeMsg) {
            isComeMsg = comeMsg;
        }
    }

    public static class ChatMessageAdapter extends BaseAdapter {

        List<ChatMessage> chatMessages = new ArrayList<>();
        private Context context;

        interface MessageViewType {
            int COM_MSG = 0;
            int TO_MSG = 1;
        }

        public ChatMessageAdapter(List<ChatMessage> chatMessages, Context context) {
            this.chatMessages = chatMessages;
            this.context = context;
        }

        @Override
        public int getCount() {
            return chatMessages.size();
        }

        @Override
        public Object getItem(int i) {
            return chatMessages.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getItemViewType(int position) {
            // 返回该view的type
            ChatMessage chatMessage = chatMessages.get(position);
            return chatMessage.isComeMsg() ? MessageViewType.COM_MSG : MessageViewType.TO_MSG;
        }

        // 有几种不同的视图
        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ChatMessage chatMessage = chatMessages.get(i);

            if (view == null) {
                if (chatMessage.isComeMsg()) {
                    view = LayoutInflater.from(context).inflate(R.layout.);
                } else {
                    view = LayoutInflater.from(context).inflate(R.layout.);
                }
            }
            return null;
        }
    }
}