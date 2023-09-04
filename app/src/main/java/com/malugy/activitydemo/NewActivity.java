package com.malugy.activitydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        // 在长按位置弹出上下文菜单栏
        // 1. 注册
        registerForContextMenu(findViewById(R.id.ctx_btn));
        // 2. 创建 覆盖onCreateContextMenu
        // 3. 菜单项操作 覆盖onContextItemSelected
        // 4. 为按钮设置上下文操作模式

        // other: 在顶部出现上下文菜单
        // 1 实现ActionMode CallBack
        // 2 在view的长按事件中启动上下文操作模式
//        findViewById(R.id.ctx_btn).setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                startActionMode(cb);
//                return false;
//            }
//        });

        // popup 弹出式菜单
        Button popBtn = findViewById(R.id.popup_btn);
        popBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 实例化PopupMenu对象（参数2：被锚定的view）
                PopupMenu menu = new PopupMenu(NewActivity.this, popBtn);
                // 2. 加载菜单资源
                menu.getMenuInflater().inflate(R.menu.popup, menu.getMenu());
                // 3. 为PopupMenu设置点击监听器
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.copy:
                                Toast.makeText(
                                        NewActivity.this, "复制", Toast.LENGTH_SHORT
                                ).show();
                                break;
                            case R.id.paste:
                                Toast.makeText(
                                        NewActivity.this, "黏贴", Toast.LENGTH_SHORT
                                ).show();
                                break;
                        }
                        return false;
                    }
                });
                // ! 4. 显示
                menu.show();
            }
        });
    }

    ActionMode.Callback cb = new ActionMode.Callback() {
        // 创建，在启动上下文操作模式时调用
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            Log.e("TAG", "创建");
            getMenuInflater().inflate(R.menu.context, menu);
            return true;
        }

        // 创建方法后调用
        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            Log.e("TAG", "准备");
            return false;
        }

        // 菜单项被点击
        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            Log.e("TAG", "selected");
            switch (menuItem.getItemId()) {
                case R.id.delete:
                    Toast.makeText(NewActivity.this, "删除", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.item1:
                    Toast.makeText(NewActivity.this, "item1", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.item2:
                    Toast.makeText(NewActivity.this, "item2", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }

        // 上下文操作模式结束时调用
        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            Log.e("TAG", "结束");
            Toast.makeText(NewActivity.this, "over", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.delete:
//                Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.item1:
//                Toast.makeText(this, "item1", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.item2:
//                Toast.makeText(this, "item2", Toast.LENGTH_SHORT).show();
//                break;
//        }
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "添加", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // 加载菜单资源
        // xml资源
//        getMenuInflater().inflate(R.menu.context, menu);

        // 纯java代码设计
        /*
            设置
            更多
                |-> 添加
                |-> 删除
         */
        // 参数1: 组id; 参数2: 菜单项id; 参数3: 序号; 参数4: title
        menu.add(1, 1, 1, "设置");
        SubMenu sub = menu.addSubMenu(1, 2, 2, "更多");
        sub.add(2, 3, 1, "添加");
        sub.add(2, 4, 2, "删除");
    }
}