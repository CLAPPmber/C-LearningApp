package com.Type;

import java.util.Date;

/**
 * Create by Fushicho on 2019/1/29
 * 定义社区个人发布历史记录单独一项的结构体
 */
public class City_history_item {

    private String message;
    private Date date;

    public City_history_item(String str,Date d){
        this.message=str;
        this.date=d;
    }


    public String getMessage(){
        return message;
    }

    public Date getDate(){
        return date;
    }
}
