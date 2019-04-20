package com.bignerdranch.android.CLearning;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.Type.VedioCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class vedio_card extends Fragment {

    private static List<VedioCard> mVedioCardList;
    private vedio_card_adapter VedioCardAdapter;
    private View view;

    public vedio_card(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_vedio_card,container,false);
        Getcard getcard = new Getcard();
        mVedioCardList = new ArrayList<>();
        getcard.start();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_video_card);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(),1);
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
                        .url("http://123.207.25.239:2332/json/Vedio.json")
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

    //转化到主线程
    private void restart(){
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onStart();
            }
        });
    }
}




