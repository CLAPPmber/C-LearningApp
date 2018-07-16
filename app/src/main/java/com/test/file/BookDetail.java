package com.test.file;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.bignerdranch.android.CLearning.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BookDetail extends AppCompatActivity {

    private TextView FileText;
    private TextView Title_Chapter;
    private String AbsPath = "Book/";
    private String bookname;
    private String chaptername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.book_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarbookdetail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        super.onCreate(savedInstanceState);
        FileText = (TextView) findViewById(R.id.filecontent);
        Title_Chapter = (TextView) findViewById(R.id.chapter);
        Intent getintent = getIntent();
        bookname = getintent.getStringExtra("bookname");
        chaptername = getintent.getStringExtra("directoryname");
        FileText.setText(load().replace("\\n","\n"));
        Title_Chapter.setText(chaptername);
    }
    //根据Bookname和chaptername从对应的txt文件中读取内容并显示出来
    public String load() {
        BufferedReader reader = null;
        AssetManager Am = getAssets();
        StringBuilder content = new StringBuilder();
        try {
            InputStream in = Am.open(AbsPath+bookname.split("\\.")[0]+"/"+chaptername);
            BufferedReader reader1 = new BufferedReader((new InputStreamReader(in)));
            String line = "";
            while ((line = reader1.readLine()) != null) {
                content.append(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
    //Back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
