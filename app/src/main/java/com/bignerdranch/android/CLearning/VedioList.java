package com.bignerdranch.android.CLearning;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.Type.Vedio;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VedioList extends AppCompatActivity {

    private String Vname;
    private TextView VedioListTile;
    private String Url;
    private List<Vedio> mVedioCardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_vedio_list);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
            actionBar.setDisplayShowTitleEnabled(false);
        }


        Intent getintent = getIntent();
        Vname = getintent.getStringExtra("vname");

        VedioListTile = (TextView)findViewById(R.id.vedio_list_title);
        VedioListTile.setText(Vname);
        //开启线程
        GetVedioList getVedioList = new GetVedioList();
        getVedioList.start();
        try{
            getVedioList.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //加载List
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.vedio_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        VedioListAdapter VLapter = new VedioListAdapter(mVedioCardList);
        recyclerView.setAdapter(VLapter);
    }

//    public void GetVedioList(){
//        final String Url = "http://123.207.25.239:2332/json/"+Vname+".json";
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url(Url)
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    String vediojson = response.body().string();
//                    Getcardata(vediojson);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
    //将json格式转化为String并存入到List中
    private void Getcardata(String vediojson){
        try{
            JSONArray jsonArray = new JSONArray(vediojson);
            for(int i = 0 ;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Vedio vedio = new Vedio(jsonObject.getString("vname"),jsonObject.getString("url"));
                mVedioCardList.add(vedio);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
    //将视频名称用线程来获取

    class GetVedioList extends Thread{
        final String Url = "http://123.207.25.239:2332/json/"+Vname+".json";
        public void run(){
            try{
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Url)
                        .build();
                Response response = client.newCall(request).execute();
                String vediojson = response.body().string();
                Getcardata(vediojson);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
