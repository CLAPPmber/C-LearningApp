package com.Type;

public class Chapter_data {
    private int now_chapter;
    private int now_question;
    private int[] chapter_progress=new int[15];
    private int[] chapter_max_num=new int[15];



    public void set_chapter_max_num(int x,int val){
        chapter_max_num[x]=val;
    }

    public int get_chapter_max_num(int x){
        return chapter_max_num[x];
    }

    public void set_chapter_progress(int x,int val){
        chapter_progress[x]=val;
    }

    public int get_chapter_progress(int x){
        return chapter_progress[x];
    }

    public void set_now_chapter(int x){
        now_chapter=x;
    }

    public int get_now_chapter(){
        return now_chapter;
    }

    public void set_now_question(int x){
        now_question=x;
    }

    public int get_now_question(){
        return now_question;
    }
}
