package com.example.myapplication;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActionModeActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_mode);

        // 初始化数据
        initData();

        // 初始化ListView
        listView = findViewById(android.R.id.list);
        adapter = new ArrayAdapter<>(
                this,
                R.layout.list_item_action_mode,
                android.R.id.text1,
                dataList
        );
        listView.setAdapter(adapter);

        // 设置ActionMode多选模式
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // 创建上下文操作栏
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_action_mode, menu);
                actionMode = mode;

                // 设置标题
                mode.setTitle("1 selected");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // 处理菜单项点击
                int id = item.getItemId();

                if (id == R.id.menu_share) {
                    shareSelectedItems();
                    mode.finish();
                    return true;
                } else if (id == R.id.menu_edit) {
                    editSelectedItem();
                    mode.finish();
                    return true;
                } else if (id == R.id.menu_delete) {
                    deleteSelectedItems();
                    mode.finish();
                    return true;
                } else if (id == R.id.menu_more) {
                    showMoreOptions();
                    return true;
                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // 清除选择状态
                clearSelection();
                actionMode = null;
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // 当选择状态改变时更新标题
                int selectedCount = listView.getCheckedItemCount();
                mode.setTitle(selectedCount + " selected");

                // 更新列表项背景
                updateItemBackground(position, checked);
            }
        });

        // 设置普通点击事件（可选）
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = dataList.get(position);
                Toast.makeText(ActionModeActivity.this,
                        "点击: " + item,
                        Toast.LENGTH_SHORT).show();
            }
        });

        // 设置长按事件（可选，因为多选模式会自动处理长按）
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 手动启动ActionMode
                listView.setItemChecked(position, true);
                return true;
            }
        });
    }

    // 初始化数据
    private void initData() {
        dataList = new ArrayList<>();
        dataList.add("One");
        dataList.add("Two");
        dataList.add("Three");
        dataList.add("Four");
        dataList.add("Five");
    }

    // 分享选中的项目
    private void shareSelectedItems() {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        StringBuilder selectedItems = new StringBuilder();

        for (int i = 0; i < checked.size(); i++) {
            if (checked.valueAt(i)) {
                int position = checked.keyAt(i);
                selectedItems.append(dataList.get(position)).append(", ");
            }
        }

        if (selectedItems.length() > 0) {
            selectedItems.setLength(selectedItems.length() - 2);
            Toast.makeText(this,
                    "分享: " + selectedItems.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // 编辑选中项目
    private void editSelectedItem() {
        int checkedCount = listView.getCheckedItemCount();
        if (checkedCount == 1) {
            SparseBooleanArray checked = listView.getCheckedItemPositions();
            for (int i = 0; i < checked.size(); i++) {
                if (checked.valueAt(i)) {
                    int position = checked.keyAt(i);
                    String item = dataList.get(position);
                    Toast.makeText(this,
                            "编辑: " + item,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        } else {
            Toast.makeText(this,
                    "请选择单个项目进行编辑",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // 删除选中项目
    private void deleteSelectedItems() {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        List<String> toRemove = new ArrayList<>();

        // 收集要删除的项目
        for (int i = checked.size() - 1; i >= 0; i--) {
            if (checked.valueAt(i)) {
                int position = checked.keyAt(i);
                toRemove.add(dataList.get(position));
            }
        }

        // 删除项目
        for (String item : toRemove) {
            dataList.remove(item);
        }

        adapter.notifyDataSetChanged();

        Toast.makeText(this,
                "删除了 " + toRemove.size() + " 个项目",
                Toast.LENGTH_SHORT).show();
    }

    // 显示更多选项
    private void showMoreOptions() {
        Toast.makeText(this,
                "更多选项",
                Toast.LENGTH_SHORT).show();
    }

    // 清除选择状态
    private void clearSelection() {
        for (int i = 0; i < listView.getCount(); i++) {
            listView.setItemChecked(i, false);
            updateItemBackground(i, false);
        }
    }

    // 更新列表项背景
    private void updateItemBackground(int position, boolean selected) {
        View view = listView.getChildAt(position - listView.getFirstVisiblePosition());
        if (view != null) {
            if (selected) {
                view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            } else {
                view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        }
    }
}