package com.Type;

/**
 * Created by Administrator on 2018\4\9 0009.
 */

public class VedioCard {
    private String Vname;
    private String Img;

    public VedioCard(String vname, String img) {
        Vname = vname;
        Img = img;
    }

    public String getVname() {
        return Vname;
    }

    public void setVname(String vname) {
        Vname = vname;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }
}
