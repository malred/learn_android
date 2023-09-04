package com.malugy.activitydemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Toast;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    public void showNormalDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("提示");
        dialog.setMessage("您确定退出程序吗?");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    public void dialogClick(View v) {
        switch (v.getId()) {
            case R.id.normal_dialog_btn:
                // AlertDialog的构造方法是protected
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("您确定退出程序吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//                showNormalDialog();
                break;
            case R.id.diy_dialog_btn:
                MyDialog md = new MyDialog(this, R.style.mydialog);
                md.show();
                break;
            case R.id.popup_btn:
                showPopupWindow(v);
                break;
            case R.id.array_btn:
                showArrayDialog();
                break;
        }
    }

    private void showArrayDialog() {
        final String[] items = {"java", "mysql", "android", "html", "c", "javascript"};
        // 数组适配器
        // 参数1: 环境; 参数2: 布局资源索引,每一项数据所呈现的样式
        // 参数3: 数据源
        ArrayAdapter adapter = new ArrayAdapter(
                // 3个参数的构造方法需要传入的layout根元素是textView
//                this, android.R.layout.simple_dropdown_item_1line, items);
                // 4个参数的构造方法多了一个指定textView的id的参数.
                // 就不需要根元素是textView
                this, R.layout.array_item_layout, R.id.item_text, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("请选择")
                // 参数1: 适配器对象(对数据显示样式的规则制定器)
                // 参数2: 监听器
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(DialogActivity.this, items[i], Toast.LENGTH_SHORT)
                                .show();
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }

    private void showPopupWindow(View anchor) {
        // 准备弹出需要的视图对象
        // 参数2: 父容器
        View v = LayoutInflater.from(this).inflate(R.layout.popup_layout, null);
        // 1. 实例化
        // 参数1：用在弹窗中的view
        // 参数2、3：弹窗的宽高（单位为px）
        // 参数4：能否获取焦点
        PopupWindow window = new PopupWindow(v, 552, 102, true);
        // 2. 设置
        // 设置背景
        // 透明背景 -> 不会影响我们在layout里设置的背景等
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置能响应外部的点击事件
        window.setOutsideTouchable(true);
        // 设置能响应点击事件
        window.setTouchable(true);
        // >1 创建动画资源 >2 创建一个style应用动画资源 >3 对当前弹窗的动画风格设置为第二步的资源索引
        window.setAnimationStyle(R.style.translate_anim);
        // 3. 显示
        // 参数1（anchor）：锚点 -> 显示在该控件的下方
        // 参数2、3：相对于锚点，在相对位置上的偏移量
        window.showAsDropDown(anchor, 100, 0);
        // 弹窗中的文本添加点击事件
        v.findViewById(R.id.choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DialogActivity.this, "选择", Toast.LENGTH_SHORT)
                        .show();
                // 弹窗消失
                window.dismiss();
            }
        });
        v.findViewById(R.id.choose_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DialogActivity.this, "全选", Toast.LENGTH_SHORT)
                        .show();
                window.dismiss();
            }
        });
        v.findViewById(R.id.copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DialogActivity.this, "复制", Toast.LENGTH_SHORT)
                        .show();
                window.dismiss();
            }
        });
    }
}