package com.Type;

public class Exer_test_msg {
    private int n;          //试题总数
    private String[] name=new String[15];
    private int[] complete=new int[15];
    private int[] total=new int[15];
    public Exer_test_msg(){
        n=0;
        for(int i=0;i<15;i++){
            name[i]="标题";
            complete[i]=0;
            total[i]=0;
        }
    }
    public void set_Exer_test_msg(int id,String name,int complete,int total){
        this.name[id]=name;
        this.complete[id]=complete;
        this.total[id]=total;
    }

    public void setN(int n){
        this.n=n;
    }

    public int getN() {
        return n;
    }

    public void setComplete(int id, int complete) {
        this.complete[id] = complete;
    }

    public String getName(int id) {
        return name[id];
    }

    public int getTotal(int id) {
        return total[id];
    }

    public int getComplete(int id) {
        return complete[id];
    }
}
