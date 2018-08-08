package com.HttpTool;

public class FeedBack<T> {
    public String msg;
    public T data;

    public FeedBack(String msg,  T data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getFbdata() {
        return data;
    }

    public void setFbdata( T fbdata) {
        this.data = fbdata;
    }

    @Override
    public String toString(){
        return "ServerCallBackModel{"+"data="+data+ ",msg="+msg+"}";
    }
}
