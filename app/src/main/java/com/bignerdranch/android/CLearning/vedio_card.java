package com.bignerdranch.android.CLearning;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.Type.VedioCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class vedio_card extends AppCompatActivity {

    private static List<VedioCard> mVedioCardList;
    private vedio_card_adapter VedioCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_vedio_card);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
        }
        Getcard getcard = new Getcard();
        mVedioCardList = new ArrayList<>();
        getcard.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        try{
//            getcard.join();
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_video_card);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        VedioCardAdapter = new vedio_card_adapter(mVedioCardList);
        recyclerView.setAdapter(VedioCardAdapter);
    }
    //线程-用来进行网络操作
    class Getcard extends Thread{
        public void run(){
            try{
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://123.207.25.239:2332/json/vedio.json")
                        .build();
                Response response = client.newCall(request).execute();
                String vediojson = response.body().string();
                Getcardata(vediojson);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //将json格式转化为String并存入到List中
    public  void Getcardata(String vediojson){
        try{
            JSONArray jsonArray = new JSONArray(vediojson);
            for(int i = 0 ;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                VedioCard vedioCard = new VedioCard(jsonObject.getString("vname"),jsonObject.getString("img"));
                mVedioCardList.add(vedioCard);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        restart();
    }
    //Back
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
    //转化到主线程
    private void restart(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onStart();
            }
        });
    }
}




