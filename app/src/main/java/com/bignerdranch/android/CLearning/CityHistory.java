package com.bignerdranch.android.CLearning;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.Type.City_history_item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CityHistory extends AppCompatActivity {

    private List<City_history_item> mCityItemList= new ArrayList<>();          //定义社区动态消息链表
    private ImageView city_bg;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton goto_top;
    private Toolbar toolbar;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_history);

        //初始化视图
        initView();

        //添加历史动态信息
        importHistory();

        //更新历史动态视图
        updata_adapter();
    }




    //初始化视图
    private void initView(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.city_history_collapsing_toolbar_layout);
        city_bg = (ImageView) findViewById(R.id.city_bg);
        toolbar = (Toolbar) findViewById(R.id.city_history_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.img_back);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setTitle(" ");

        //设置社区背景图片
        city_bg.setImageResource(R.drawable.city_bg_pic);

        recyclerView =(RecyclerView) findViewById(R.id.city_history_recyclerView);

        goto_top=(FloatingActionButton)findViewById(R.id.city_history_goto_top);
        //悬浮按钮的点击事件的监听
        goto_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);         //回到第一条记录处
            }
        });

    }



    //添加历史动态信息
    private void importHistory(){
        for(int i=0;i<11;i++) {
            Date date = new Date(2019, 1, 29, 20, 26);
            City_history_item ci = new City_history_item("加班加班啦!!!", date);
            mCityItemList.add(ci);
        }
    }

    //更新历史动态视图
    private void updata_adapter(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(CityHistory.this);
        recyclerView.setLayoutManager(layoutManager);
        CityHistoryItem adapter =new CityHistoryItem(mCityItemList);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onDestroy() {                                          //重写活动销毁前调用的函数
        super.onDestroy();
        mCityItemList.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}

