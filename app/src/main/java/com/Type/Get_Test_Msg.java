package com.Type;

public class Get_Test_Msg {
    private String question_name;
    private String question_a;
    private String question_b;
    private String question_c;
    private String question_d;
    private String question_ans;
    public Get_Test_Msg(String name,String a,String b,String c,String d,String ans){
        this.question_name=name;
        this.question_a=a;
        this.question_b=b;
        this.question_c=c;
        this.question_d=d;
        this.question_ans=ans;
    }

    public String getQuestion_ans() {
        return question_ans;
    }

    public String getQuestion_a() {
        return question_a;
    }

    public String getQuestion_b() {
        return question_b;
    }

    public String getQuestion_c() {
        return question_c;
    }

    public String getQuestion_d() {
        return question_d;
    }

    public String getQuestion_name() {
        return question_name;
    }

}
