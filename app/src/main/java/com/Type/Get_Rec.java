package com.Type;

public class Get_Rec {
    private int question_num;
    private String question_ans;
    public Get_Rec(int num,String ans){
        this.question_ans=ans;
        this.question_num=num;
    }

    public int getQuestion_num() {
        return question_num;
    }

    public String getQuestion_ans() {
        return question_ans;
    }
}
