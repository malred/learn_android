package com.malugy.activitydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ImageViewPagerAdapter extends AppCompatActivity {

    public static final int INIT_POSITION = 1;
    private ViewPager viewPager;
    private int[] layoutIds = {
            R.layout.view_first,
            R.layout.view_second,
            R.layout.view_third,
    };
    private List<View> views;

    private ViewGroup dotViewGroup;
    // 下方显示的点
    private List<ImageView> dotViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_pager_adapter);

        viewPager = findViewById(R.id.view_pager);
        dotViewGroup = findViewById(R.id.dot_layout);
        // 初始化数据
        views = new ArrayList<>();
        for (int i = 0; i < layoutIds.length; i++) {
//            View view = getLayoutInflater().inflate(layoutIds[i], null);
//            views.add(view);

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);
            views.add(imageView);

            ImageView dot = new ImageView(this);
            dot.setImageResource(R.mipmap.ic_launcher);
            dot.setMaxWidth(100);
            dot.setMaxHeight(100);

            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(40, 40);
            layoutParams.leftMargin = 20;
            dot.setLayoutParams(layoutParams);
            dot.setEnabled(false);

            dotViewGroup.addView(dot);
            dotViews.add(dot);
        }
        // 设置adapter
        viewPager.setAdapter(pagerAdapter);
        // 当划到某个view时,其实前后的view也被加载了,
        // 除了前后两个还有自身的其他view都会被销毁,所以不会内存爆炸
        // 设置两侧各保留多少个view不销毁
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(INIT_POSITION);
        // 因为上面设置current不会触发onPageSelected,所以这里手动更新
        setDotViews(INIT_POSITION);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // page滚动时

            }

            @Override
            public void onPageSelected(int position) {
                // position是滑动结果，也就是新页面的的index
                // 必须切到一个跟上一个不一样的页面才会调用（另两个不需要）
                setDotViews(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 滚动->不滚动 or 不滚动->滚动
            }
        });
    }

    private void setDotViews(int position) {
        for (int i = 0; i < dotViews.size(); i++) {
            dotViews.get(i).setImageResource(
                    position == i ? R.mipmap.star : R.mipmap.ic_launcher);
        }
    }

    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return layoutIds.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            // 添加视图
            View child = views.get(position);
            container.addView(child);
            return child;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
        }
    };
}