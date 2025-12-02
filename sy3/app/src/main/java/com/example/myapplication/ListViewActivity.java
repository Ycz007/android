package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {


    private ListView listView;

    private String[] strings={"cat","dog","elephant","lion","monkey","tiger"};
    private int[] images={R.drawable.cat,R.drawable.dog,R.drawable.elephant,
                          R.drawable.lion,R.drawable.monkey,R.drawable.tiger};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view);

        listView=findViewById(R.id.lv);

        List<Map<String,Object>> list=new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", strings[i]);      // 动物名称
            map.put("image", images[i]);      // 动物图片
            list.add(map);
        }

        String[] from = {"name", "image"};

        int[] to={R.id.tv_animal_name,R.id.iv_animal_img};


        //装配simpleAdapter
        SimpleAdapter simpleAdapter=new SimpleAdapter(
                this,
                        list,
                R.layout.list_item,
                from,
                to
        );

        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this,strings[position],Toast.LENGTH_SHORT).show();
            }
        });


    }
}


