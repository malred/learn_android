package com.malugy.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button15:
                startActivity(new Intent(ListActivity.this, ListViewActivity.class));
                break;
            case R.id.button16:
                startActivity(new Intent(ListActivity.this, RequestDataActivity.class));
                break;
            case R.id.button17:
                startActivity(new Intent(ListActivity.this, ChatActivity.class));
                break;
        }
    }
}