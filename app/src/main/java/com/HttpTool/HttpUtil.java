package com.HttpTool;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static FeedBack ParseJson(String jsonData){
        Gson gson = new Gson();
        /*List<FeedBack> fblist = gson.fromJson(jsonData,new TypeToken<List<FeedBack>>(){}.getType());*/
        FeedBack<User[]> fb = gson.fromJson(jsonData,FeedBack.class);
        return fb;
    }
}
