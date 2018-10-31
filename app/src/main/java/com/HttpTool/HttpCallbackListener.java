package com.HttpTool;

import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

//监听http Response，并回调OnSuccess和onFailure
public class HttpCallbackListener implements okhttp3.Callback {
  private HttpCallBack mHttpCallBack;
  @Override
  public void onResponse(Call call, Response response) throws IOException {
    if(response.code()!=200){
      mHttpCallBack.onFailed(response.code(),response.message());
      return;
    }
    String fb = response.body().string();
    HttpUtil.getRequest(fb,null,mHttpCallBack);
  }
  @Override
  public void onFailure(Call call,IOException e){
    //handle http fail
    mHttpCallBack.onFailed(404,"网络出错");

  }
  public HttpCallbackListener(HttpCallBack httpCallBack) {
    mHttpCallBack = httpCallBack;
  }
}
