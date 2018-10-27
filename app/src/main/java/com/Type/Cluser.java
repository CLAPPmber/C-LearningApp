package com.Type;

public class Cluser {
    private  String Account;
    private  String Password;
    public Cluser(String account,String password){
        Account = account;
        Password = password;
    }

    public String getAccount() {
        return Account;
    }

    public String getPassword() {
        return Password;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
