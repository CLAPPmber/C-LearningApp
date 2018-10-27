package com.HttpTool;

import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;
//监听http Response，并回调OnSuccess和onFailure
public class HttpCallbackListener implements okhttp3.Callback {
  private HttpCallBack mHttpCallBack;
  @Override
  public void onResponse(Call call, Response response) throws IOException {
    String fb = response.body().string();
    HttpUtil.getRequest(fb,null,mHttpCallBack);
  }
  @Override
  public void onFailure(Call call,IOException e){
    //handle http fail
  }
  public HttpCallbackListener(HttpCallBack httpCallBack) {
    mHttpCallBack = httpCallBack;
  }
}
