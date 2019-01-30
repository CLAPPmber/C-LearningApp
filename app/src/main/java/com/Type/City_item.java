package com.Type;

import java.util.Date;

/**
 * Create by Fushicho on 2019/1/29
 * 用于定义社区每条动态的结构体
 */
public class City_item {

    private String author_name;
    private String message;
    private Date date;

    public City_item(String name,String mes,Date d){
        this.author_name=name;
        this.message=mes;
        this.date=d;
    }

    public String getAuthor_name() {
        return author_name;
    }
    public String getMessage(){
        return message;
    }
    public Date getDate(){
        return date;
    }
}
