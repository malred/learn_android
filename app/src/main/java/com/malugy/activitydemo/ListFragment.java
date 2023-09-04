package com.malugy.activitydemo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 列表fragment
 */
public class ListFragment extends Fragment {
    // 提取成常量,方便复用
    public static final String BUNDLE_TITLE = "bundle_title";
    private String title = "imooc";

    public static ListFragment newInstance(String title) {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        // 传值
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(BUNDLE_TITLE);
        }
    }

    // ! 创建视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // 创建视图
        // 参数1: 布局文件; 参数2: 当前fragment所在的group; 参数3: 是否绑定到当前根布局
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        TextView textView = view.findViewById(R.id.tv);
        textView.setText(title);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTitleClickListener != null) {
                    onTitleClickListener.onClick(title);
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public OnTitleClickListener getOnTitleClickListener() {
        return onTitleClickListener;
    }

    // 设置接口的方法
    public void setOnTitleClickListener(OnTitleClickListener onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
    }

    // 定义变量
    OnTitleClickListener onTitleClickListener;

    // 定义接口
    public interface OnTitleClickListener {
        void onClick(String title);
    }
}
