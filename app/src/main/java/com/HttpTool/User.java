package com.HttpTool;

public class User {
    public String account;
    public String password;
    public String userhead;

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public User(String account) {
        this.account = account;
        this.password = "";
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getuserHeadImage() {
        return userhead;
    }

    public void setuserHeadImage(String userHeadImage) {
        this.userhead = userHeadImage;
    }
}
