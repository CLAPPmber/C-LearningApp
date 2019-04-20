package com.Type;

public class Get_Progress {
    private int test_num;
    private int test_complete_count;
    public Get_Progress(int num,int count){
        this.test_num=num;
        this.test_complete_count=count;
    }

    public int getTest_num() {
        return test_num;
    }

    public int getTest_complete_count() {
        return test_complete_count;
    }
}
