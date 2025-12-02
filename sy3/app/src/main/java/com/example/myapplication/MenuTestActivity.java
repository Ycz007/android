package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MenuTestActivity extends AppCompatActivity {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);

        // 初始化测试文本
        tvTest = findViewById(R.id.tv_test);
    }

    // 1. 创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载菜单资源文件
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // 2. 处理菜单项点击
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_font_small) {
            // 小号字体：10sp
            tvTest.setTextSize(10);
            showToast("字体大小：小(10号字)");
            return true;

        } else if (itemId == R.id.menu_font_medium) {
            // 中号字体：16sp
            tvTest.setTextSize(16);
            showToast("字体大小：中(16号字)");
            return true;

        } else if (itemId == R.id.menu_font_large) {
            // 大号字体：20sp
            tvTest.setTextSize(20);
            showToast("字体大小：大(20号字)");
            return true;

        } else if (itemId == R.id.menu_normal) {
            // 普通菜单项：显示Toast
            showToast("普通菜单项被点击");
            return true;

        } else if (itemId == R.id.menu_color_red) {
            // 红色字体
            tvTest.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            showToast("字体颜色：红色");
            return true;

        } else if (itemId == R.id.menu_color_black) {
            // 黑色字体
            tvTest.setTextColor(getResources().getColor(android.R.color.black));
            showToast("字体颜色：黑色");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 显示Toast的辅助方法
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}