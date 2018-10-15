package com.ImgTool;

import android.graphics.Bitmap;

public class MarkSetImg {
    private String Position;
    private Bitmap bitmap;

    public MarkSetImg(String position, Bitmap bitmap) {
        Position = position;
        this.bitmap = bitmap;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
