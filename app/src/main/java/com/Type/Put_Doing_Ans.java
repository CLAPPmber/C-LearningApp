package com.Type;

public class Put_Doing_Ans {
    private int flag;
    private int test_num;
    private int question_num;
    private String question_ans;
    public Put_Doing_Ans(int flag,int t_num,int q_num,String ans){
        this.flag=flag;
        this.test_num=t_num;
        this.question_num=q_num;
        this.question_ans=ans;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setQuestion_ans(String question_ans) {
        this.question_ans = question_ans;
    }

    public void setTest_num(int test_num) {
        this.test_num = test_num;
    }
    public void setQuestion_num(int num){
        this.question_num=num;
    }
}
