package com.Type;

/**
 * Created by Administrator on 2017\11\27 0027.
 */

public class Book {
    private String BookName;
    private int ImgId;
    private int Correct;
    public int getCorrect() {
        return Correct;
    }

    public void setCorrect(int correct) {
        this.Correct = correct;
    }

    public Book(String BN, int ID,int correct){
        BookName = BN;
        ImgId = ID;
        Correct = correct;
    }
    public String getBookName() {
        return BookName;
    }
    public int getImgId() {
        return ImgId;
    }


}
