package com.HttpTool;

public class Chap {
    public String account;

    private class Record{
        private int  chapter_num;
        private int question_num;
    }

    public Record[] record=new Record[15];

    public Chap(String account,int progress[]) {
        this.account = account;
        for(int i=1;i<=11;i++){
             record[i].chapter_num=i;
             record[i].question_num=progress[i];
        }

    }

    /*
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getChapter_progress() {
        return chapter_progress;
    }

    public void setChapter_progress(String chapter_progress) {
        this.chapter_progress = chapter_progress;
    }*/
}
