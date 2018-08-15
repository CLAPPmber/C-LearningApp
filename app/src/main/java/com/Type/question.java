package com.Type;

public class question {
    private int chapter_num;
    private int question_num;
    private String question_name;
    private int question_ans;
    private String question_analysis;
    private String question_a;
    private String question_b;
    private String question_c;
    private String question_d;

    public question(int chapter_id,int question_id,String name,int ans,String ana,String q_a,String q_b,String q_c,String q_d){
        chapter_num=chapter_id;
        question_num=question_id;
        question_name= name;
        question_ans=ans;
        question_analysis=ana;
        question_a=q_a;
        question_b=q_b;
        question_c=q_c;
        question_d=q_d;
    }

    public int    get_chapter_num(){return chapter_num;};
    public int    get_question_num(){return question_num;};
    public String get_question_name(){return question_name;};
    public int    get_question_ans(){return question_ans;};
    public String get_question_analysis(){return question_analysis;};
    public String get_question_a(){return question_a;};
    public String get_question_b(){return question_b;};
    public String get_question_c(){return question_c;};
    public String get_question_d(){return question_d;};
}
