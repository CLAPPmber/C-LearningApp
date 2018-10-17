package com.Type;

/**
 * Created by Administrator on 2018\4\9 0009.
 */

public class Vedio {
  private String VedioName;
  private String Url;

  public Vedio(String vedioName, String url) {
    VedioName = vedioName;
    Url = url;
  }

  public String getVedioName() {
    return VedioName;
  }

  public void setVedioName(String vedioName) {
    VedioName = vedioName;
  }

  public String getUrl() {
    return Url;
  }

  public void setUrl(String url) {
    Url = url;
  }
}
