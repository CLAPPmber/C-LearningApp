package com.Type;

public class Retp_User_Progress {
    private int completed;
    private int total;
    public Retp_User_Progress(int com, int tot) {
        completed = com;
        total = tot;
    }

    public int getCompleted() {
        return completed;
    }

    public int getTotal() {
        return total;
    }
}
