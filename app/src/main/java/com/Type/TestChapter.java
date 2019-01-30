package com.Type;

/**
 * Create by Fushicho on 2019/1/24
 * 定义试题训练章节目录的结构体
 */

public class TestChapter {
    private int    test_num;                                      //习题目录编号
    private String test_fir_name;                            //习题章节目录主标题
    private String test_sec_name;                            //习题章节目录副标题
    private String progress;                                 //该套试题做题进度
    private int test_id;                                     //试题编号(用于获取服务器试题数据)


    public TestChapter(int num,String fir,String sec,String pro,int id) {   //构造函数
        this.test_num=num;
        this.test_fir_name=fir;
        this.test_sec_name = sec;
        this.progress = pro;
        this.test_id = id;
    }

    public int    getTest_num(){return test_num;}
    public String getTest_fir_name(){
        return test_fir_name;
    }
    public String getTest_sec_name(){
        return test_sec_name;
    }
    public String getProgress(){
        return progress;
    }
    public int    getTest_id(){
        return test_id;
    }
}
