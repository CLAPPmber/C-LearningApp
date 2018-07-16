package com.Type;

/**
 * Created by Administrator on 2017\11\26 0026.
 */

public class Chapter {
    private String chaptername;

    int questionnum;

    public Chapter(String name,int num){
        this.chaptername = name;
        this.questionnum = num;
    }

    public String getchaptername(){
        return chaptername;
    }
    public int getQuestionnum() {
        return questionnum;
    }
}
