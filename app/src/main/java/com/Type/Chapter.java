package com.Type;

/**
 * Created by Administrator on 2017\11\26 0026.
 */

public class Chapter {
    private String chaptername;

    private String questionnum;

    public Chapter(String name,String num){
        this.chaptername = name;
        this.questionnum = num;
    }

    public String getchaptername(){
        return chaptername;
    }
    public String getQuestionnum() {
        return questionnum;
    }
}
