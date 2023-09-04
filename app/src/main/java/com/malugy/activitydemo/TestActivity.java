package com.malugy.activitydemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btn = findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button btn2 = findViewById(R.id.button_finish);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        if (getIntent() != null) {
            String stringExtra = getIntent().getStringExtra(LifeCycleActivity.BUTTON_TITLE);
            if (stringExtra != null) {
                Log.i("TAG", stringExtra);
            }
            Bundle bundle = getIntent().getBundleExtra(LifeCycleActivity.BUTTON_TITLE);
            if (bundle != null) {
                Log.i("TAG", bundle.getString(LifeCycleActivity.BUTTON_TITLE));
            }
            Toast.makeText(TestActivity.this, "111", Toast.LENGTH_SHORT).show();
        }
    }
}