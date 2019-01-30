package com.Type;

import java.util.Date;

/**
 * Create by Fushicho on 2019/1/29
 * 定义动态评论的类
 */


public class City_Command_item {

    private String author_name;
    private String author_message;
    private Date date;

    public City_Command_item(String name,String mes,Date d){
        this.author_name=name;
        this.author_message=mes;
        this.date=d;
    }

    public String getAuthor_name(){
        return author_name;
    }

    public String getAuthor_message(){
        return author_message;
    }

    public Date getDate(){
        return date;
    }
}
