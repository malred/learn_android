package com.malugy.activitydemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TestFragment extends Fragment {

    public static final String POSITION = "position";
    public static final String TITLE = "title";
    private String position;
    private String title;
    private static String flag;

    // 不建议直接传递参数给fragment的构造函数
    // 建议使用一个方法接收并使用参数
    public static TestFragment newInstance(int position) {
        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);
        flag = "position";
        return fragment;
    }

    public static TestFragment newInstance(String title) {
        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        fragment.setArguments(bundle);
        flag = "title";
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            switch (flag) {
                case "title":
                    title = getArguments().getString(TITLE);
                    break;
                case "position":
                    position = String.valueOf(getArguments().getInt(POSITION));
                    break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);
        TextView textView = view.findViewById(R.id.text_view);

        switch (flag) {
            case "title":
                textView.setText(title);
                break;
            case "position":
                textView.setText(position);
                break;
        }

        return view;
    }
}


