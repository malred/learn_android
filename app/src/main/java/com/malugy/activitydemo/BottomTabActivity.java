package com.malugy.activitydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class BottomTabActivity extends AppCompatActivity implements TabHost.TabContentFactory {

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);

        // 初始化总布局
        tabHost = findViewById(R.id.tab_host);
        tabHost.setup();

        // 3个tab,处理
        // .1 init data
        int[] titleIds = {
                R.string.home,
                R.string.me,
                R.string.message,
        };
        int[] drawableIds = {
                R.drawable.main_tab_icon_home,
                R.drawable.main_tab_icon_me,
                R.drawable.main_tab_icon_msg,
        };

        // .2 data <--> view
        for (int i = 0; i < titleIds.length; i++) {
            View view = getLayoutInflater()
                    .inflate(R.layout.mytab_layout, null, false);

            ImageView icon = view.findViewById(R.id.main_tab_icon);
            TextView title = view.findViewById(R.id.main_tab_txt);
            View tab = view.findViewById(R.id.tab_bg);

            icon.setImageResource(drawableIds[i]);
            title.setText(titleIds[i]);

            tab.setBackgroundColor(getResources().getColor(R.color.white));

            tabHost.addTab(
                    tabHost.newTabSpec(getString(titleIds[i]))
                            // 分割
                            .setIndicator(view)
                            // 因为tab必须要内容，但是我们是通过viewpager设置的
                            // 不需要content，所以这里随便弄一下
                            .setContent(this)
            );
        }

        // 3个fragment，组成viewpager

        Fragment[] fragments = new Fragment[]{
                TestFragment.newInstance("home"),
                TestFragment.newInstance("message"),
                TestFragment.newInstance("me")
        };

        ViewPager viewPager = findViewById(R.id.view_pager1);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (tabHost != null) {
                    // 设置当前tab
                    tabHost.setCurrentTab(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (tabHost != null) {
                    int position = tabHost.getCurrentTab();
                    viewPager.setCurrentItem(position);
                }
            }
        });
    }

    @Override
    public View createTabContent(String s) {
        View view = new View(this);
        view.setMinimumHeight(0);
        view.setMinimumWidth(0);
        return view;
    }
}