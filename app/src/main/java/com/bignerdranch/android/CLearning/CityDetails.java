package com.bignerdranch.android.CLearning;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.Type.City_Command_item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by Fushicho on 2019/1/29
 * 社区动态详情界面
 */

public class CityDetails extends AppCompatActivity {


    private List<City_Command_item> mCityCommandItem= new ArrayList<>();          //定义评论链表
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);

        initView();                  //初始化视图
        importCommand();             //添加评论信息
        updata_adapter();            //更新评论视图
    }


    //初始化视图
    private void initView(){
        //创建返回行
        Toolbar toolbar = (Toolbar) findViewById(R.id.city_detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }


    //添加评论信息
    private void importCommand(){
        for(int i=0;i<6;i++) {
            Date date = new Date(2019, 1, 29, 20, 26+i);
            City_Command_item ci = new City_Command_item("Fushicho", "继续加班加班!!!", date);
            mCityCommandItem.add(ci);
        }
    }

    //更新评论视图
    private void updata_adapter(){
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.city_detail_recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(CityDetails.this);
        recyclerView.setLayoutManager(layoutManager);
        CityCommandItem adapter =new CityCommandItem(mCityCommandItem);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onDestroy() {                                          //重写活动销毁前调用的函数
        super.onDestroy();
        mCityCommandItem.clear();
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
