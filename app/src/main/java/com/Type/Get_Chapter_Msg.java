package com.Type;

public class Get_Chapter_Msg {
    private int test_num;
    private String test_name;
    private int test_total;

    public Get_Chapter_Msg(int num,String name,int total){
        this.test_num=num;
        this.test_name=name;
        this.test_total=total;
    }

    public int getTest_num() {
        return test_num;
    }

    public int getTest_total() {
        return test_total;
    }

    public String getTest_name() {
        return test_name;
    }
}
