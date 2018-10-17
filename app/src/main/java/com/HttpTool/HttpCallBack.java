package com.HttpTool;

import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;
/*
OnSserverCallBack和OnCommonCallBack的父类
 */

public abstract class HttpCallBack<T>  {
  private Type mGenericSuperclass;

  public HttpCallBack() {
    Type genericSuperclass = getClass().getGenericSuperclass();
    if (genericSuperclass instanceof ParameterizedType) {
      mGenericSuperclass = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
    } else {
      mGenericSuperclass = Object.class;
    }
  }

  public abstract void onResolve(T t);

  public abstract void onFailed(int code, String msg);

  public Type getType() {
    return mGenericSuperclass;
  }


}
