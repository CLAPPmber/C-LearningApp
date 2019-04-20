package com.HttpTool;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/*
Http操作工具，使用sendOkHttpGetRequest来发送Get请求，sendOkHttpPostRequest来发送Post请求。
 */
public class HttpUtil {
  private static Gson mGson;
  private static HttpCallbackListener mHttpCallbackListener;
  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
  public static void sendOkHttpGetRequest(String url, final HttpCallBack httpCallBack){
    mHttpCallbackListener = new HttpCallbackListener(httpCallBack);
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url(url)
            .get()
            .build();
    client.newCall(request).enqueue(mHttpCallbackListener);
  }

  public static void sendOkHttpPostRequest(String url,String json,final HttpCallBack httpCallBack){
    mHttpCallbackListener = new HttpCallbackListener(httpCallBack);
    RequestBody body = RequestBody.create(JSON,json);
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();
    client.newCall(request).enqueue(mHttpCallbackListener);
  }

  //上传图片文件到服务器
  public static void sendOkHttpUploadImageFile(String url, File file ,final HttpCallBack httpCallBack){
    mHttpCallbackListener = new HttpCallbackListener(httpCallBack);
    OkHttpClient client = new OkHttpClient();
    RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("file",file.getName(),RequestBody.create(MediaType.parse("multipart/form-data"),file)).build();
    Request request = new Request.Builder()
            .url(url)
            .post(requestBody)
            .build();
    client.newCall(request).enqueue(mHttpCallbackListener);
  }


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


