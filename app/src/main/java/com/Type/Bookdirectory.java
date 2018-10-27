package com.Type;

/**
 * Created by Administrator on 2018\3\19 0019.
 */

public class Bookdirectory {
  private String BookName;
  private String DirectoryName;
  private String Page;
  private int Correct;
  public int getCorrect() {
    return Correct;
  }

  public void setCorrect(int correct) {
    this.Correct = correct;
  }

  public String getPage() {
    return Page;
  }

  public void setPage(String page) {
    Page = page;
  }

  public Bookdirectory(String bookName, String directoryName,String page,int correct) {
    BookName = bookName;
    DirectoryName = directoryName;
    Page = page;
    Correct = correct;
  }

  public String getBookName() {
    return BookName;
  }

  public void setBookName(String bookName) {
    BookName = bookName;
  }

  public String getDirectoryName() {
    return DirectoryName;
  }

  public void setDirectoryName(String directoryName) {
    DirectoryName = directoryName;
  }
}
