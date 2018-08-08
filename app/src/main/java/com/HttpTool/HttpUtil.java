package com.HttpTool;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

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

    private static Gson mGson;

    public static void getRequest(String url, Map<String, String> params, HttpCallBack callBack) {
        if (callBack == null) {
            return;
        }

        if (mGson == null) {
            mGson = new Gson();
        }

        boolean returnJson = false;
        Type type = callBack.getType();

        if (type instanceof Class) {
            switch (((Class) type).getSimpleName()) {
                case "Object":
                case "String":
                    returnJson = true;
                    break;
                default:
                    break;
            }
        }
        if (returnJson) {
            try {
                callBack.onResolve(url);
            } catch (Exception e) {
                callBack.onFailed(-1, e.getMessage());
                e.printStackTrace();
            }
        } else {
            try {
                callBack.onResolve(mGson.fromJson(url, type));
            } catch (Exception e) {
                callBack.onFailed(-1, e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
