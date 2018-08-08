package com.HttpTool;

import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class HttpCallbackListener implements okhttp3.Callback {
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        FeedBack<ArrayList<User>> fbdata =  HttpUtil.ParseJson(response.body().string());
        Log.d("test",fbdata.toString());
        Log.e("msg",fbdata.msg);


    }
    @Override
    public void onFailure(Call call,IOException e){
        //
    }
}
