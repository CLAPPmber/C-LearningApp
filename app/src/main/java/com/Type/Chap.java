package com.Type;

//包含一个题目记录数组和用户
public class Chap {
    public String account;
    public Record record[];

    public Chap(String account, Record[] record) {
        this.account = account;
        this.record = record;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Record[] getRecord() {
        return record;
    }

    public void setRecord(Record[] record) {
        this.record = record;
    }
}
