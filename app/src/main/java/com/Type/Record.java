package com.Type;

//某章节单个题目的记录
public class Record {
    private int  chapter_num;
    private int question_num;

    public Record(int chapter_num, int question_num) {
        this.chapter_num = chapter_num;
        this.question_num = question_num;
    }

    public int getChapter_num() {
        return chapter_num;
    }

    public void setChapter_num(int chapter_num) {
        this.chapter_num = chapter_num;
    }

    public int getQuestion_num() {
        return question_num;
    }

    public void setQuestion_num(int question_num) {
        this.question_num = question_num;
    }
}
