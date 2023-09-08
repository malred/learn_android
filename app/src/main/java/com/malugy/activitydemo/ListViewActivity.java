package com.malugy.activitydemo;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView appListView = findViewById(R.id.app_list_view);

        // 数据
//        List<String> appNames = new ArrayList<>();
//        appNames.add("qq");
//        appNames.add("wx");
//        appNames.add("mooc");

        List<ResolveInfo> appInfos = getAppInfos();
        appListView.setAdapter(new AppListAdapter(appInfos));

        // 添加头部view
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerV = inflater.inflate(R.layout.header_list_view, null);
        appListView.addHeaderView(headerV);

        // 每个item的点击事件
//        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String packageName = appInfos.get(i).activityInfo.packageName;
//                String className = appInfos.get(i).activityInfo.name;
//
//                ComponentName componentName = new ComponentName(packageName, className);
//                final Intent intent = new Intent();
//                intent.setComponent(componentName);
//                startActivity(intent);
//            }
//        });
    }

    // 获取系统已安装的应用列表
    private List<ResolveInfo> getAppInfos() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return getPackageManager().queryIntentActivities(intent, 0);
    }

    public class AppListAdapter extends BaseAdapter {
        // 要填充的数据
//        List<String> data;
        List<ResolveInfo> data;

//        public AppListAdapter(List<String> data) {
//            this.data = data;
//        }

        public AppListAdapter(List<ResolveInfo> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            // 有多少条数据
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            // 当前位置这条
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            // 当前位置这条的id
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            // 处理 view--data 填充数据的过程

            ViewHolder viewHolder = new ViewHolder();
            // 优化: 之前每次都要重新创建item,现在可以保存数据到holder,复用
            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // 得到view
                view = layoutInflater.inflate(R.layout.item_app_list_view, null);

                viewHolder.iconView = view.findViewById(R.id.icon_img_view);
                viewHolder.appNameV = view.findViewById(R.id.app_txt_v);

                // 将该view和holder建立对应关系
                // 保存到tag
                view.setTag(viewHolder);
            } else {
                // 从view中取出
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.appNameV.setText(data.get(i).activityInfo.loadLabel(getPackageManager()));
            viewHolder.iconView.setImageDrawable(data.get(i).activityInfo.loadIcon(getPackageManager()));

            // 点击跳转应用
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 包名
                    String packageName = data.get(i).activityInfo.packageName;
                    String className = data.get(i).activityInfo.name;

                    ComponentName componentName = new ComponentName(packageName, className);
                    final Intent intent = new Intent();
                    intent.setComponent(componentName);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    public class ViewHolder {
        public ImageView iconView;
        public TextView appNameV;
    }
}