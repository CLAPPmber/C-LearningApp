package com.bignerdranch.android.CLearning;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.Type.City_item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CitySystem extends AppCompatActivity {

    private List<City_item> mCityItemList= new ArrayList<>(); ;         //定义社区动态消息链表
    private ImageView city_bg;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton goto_top;
    private FloatingActionButton see_history;
    private Toolbar toolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_system);

        initView();                    //初始化视图
        importCityItem();              //从本地数据库获取社区动态信息读入运行内存
        updata_item();                 //更新社区动态信息
    }

    //初始化视图
    private void initView(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        city_bg = (ImageView) findViewById(R.id.city_bg);
        toolbar = (Toolbar) findViewById(R.id.city_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.img_back);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setTitle(" ");

        recyclerView =(RecyclerView) findViewById(R.id.city_recyclerView);


        //设置社区背景图片
        city_bg.setImageResource(R.drawable.city_bg_pic);

        see_history=(FloatingActionButton)findViewById(R.id.city_history);
        see_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CitySystem.this, CityHistory.class);
                startActivity(intent);
            }
        });


        goto_top=(FloatingActionButton)findViewById(R.id.city_goto_top);
        //悬浮按钮的点击事件的监听
        goto_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

    }

    //从本地数据库获取社区动态信息读入运行内存
    private void importCityItem(){
        for(int i=0;i<11;i++) {
            Date date = new Date(2019, 1, 29, 16, 26);
            City_item ci = new City_item("Fushicho", "加班加班啦!!!", date);
            mCityItemList.add(ci);
        }
    }

    //更新社区动态信息
    private void   updata_item(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(CitySystem.this);
        recyclerView.setLayoutManager(layoutManager);
        CityItem adapter =new CityItem(mCityItemList);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {                 //重写菜单栏
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_wri_message:
                Intent intent = new Intent(CitySystem.this, WriteMessage.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {                                          //重写活动销毁前调用的函数
        super.onDestroy();
        mCityItemList.clear();
    }
}
