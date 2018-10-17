package com.Type;

/**
 * Created by Administrator on 2017\11\26 0026.
 */

public class Chapter {
  private String chaptername;
  private int chapter_num;
  private String question_num;

  public Chapter(String name,String num,int num2){
    this.chaptername = name;
    this.question_num = num;
    this.chapter_num=num2;
  }

  public String getchaptername(){
    return chaptername;
  }
  public String getQuestionnum() {
    return question_num;
  }
  public int get_chapter_num() {
    return chapter_num;
  }
}
