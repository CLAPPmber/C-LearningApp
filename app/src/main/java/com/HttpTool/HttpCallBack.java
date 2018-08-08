package com.HttpTool;

import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public abstract class HttpCallBack<T> implements okhttp3.Callback {
    private Type mGenericSuperclass;
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            mGenericSuperclass = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            mGenericSuperclass = Object.class;
        }
    }
    public abstract void onResolve(T t);
    @Override
    public void onFailure(Call call,IOException e){

    }
    public Type getType() {
        return mGenericSuperclass;
    }

}
