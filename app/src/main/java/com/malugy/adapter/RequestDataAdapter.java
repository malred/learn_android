package com.malugy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.malugy.activitydemo.R;
import com.malugy.model.LessonInfo;

import java.util.ArrayList;
import java.util.List;

public class RequestDataAdapter extends BaseAdapter {

    List<LessonInfo> lessonInfos = new ArrayList<>();
    Context context;

    public RequestDataAdapter(List<LessonInfo> lessonInfos, Context context) {
        this.lessonInfos = lessonInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lessonInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return lessonInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_app_list_view, null);

            viewHolder.iconView = view.findViewById(R.id.icon_img_view);
            viewHolder.appNameV = view.findViewById(R.id.app_txt_v);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.appNameV.setText(lessonInfos.get(i).getName());
        viewHolder.iconView.setVisibility(View.GONE);

        return view;
    }


    public class ViewHolder {
        public ImageView iconView;
        public TextView appNameV;
    }
}
