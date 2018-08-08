package com.HttpTool;

public class FeedBack<T> {
    public String msg;
    public T data;
    public int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        code = code;
    }

    public FeedBack(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return "ServerCallBackModel{"+"data="+data+ ",msg="+msg+"}";
    }
}
