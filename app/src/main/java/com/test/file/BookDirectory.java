package com.test.file;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.Type.Bookdirectory;
import com.bignerdranch.android.CLearning.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BookDirectory extends AppCompatActivity {

    public static List<Bookdirectory> DirectoryList = new ArrayList<>();
    private String Bookname;
    private String AbsPath = "Book/";
    private String[] Line;
    private int Correct;
    private TextView booktitle;
    private static final String TAG = "BookDirectory";

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            setContentView(R.layout.book_directory);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarbookdrt);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.img_back);
                actionBar.setDisplayShowTitleEnabled(false);
            }
            super.onCreate(savedInstanceState);

            Intent getintent = getIntent();
            Bookname = getintent.getStringExtra("bookname");
            Correct = getintent.getIntExtra("correct",0);
            booktitle = (TextView)findViewById(R.id.book_directory_title);
            booktitle.setText(Bookname.split("\\.")[0]);
            getDirectory();

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.book_directory);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            BookDirectoryAdapter Bdadapter = new BookDirectoryAdapter(DirectoryList,false);
            recyclerView.setAdapter(Bdadapter);
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //从Directory文件中读取书本目录并放入到DirectoryList中
    private void getDirectory(){
        DirectoryList.clear();
        BufferedReader reader = null;
        AssetManager Am = getAssets();
        try {
            InputStream in =  Am.open(AbsPath+Bookname.split("\\.")[0]+"/"+Bookname.split("\\.")[0]+"Directory");
            reader = new BufferedReader((new InputStreamReader(in)));
            String line = "";
            while ((line = reader.readLine()) != null) {
                Line =  line.split("/");
                Bookdirectory Bookdt = new Bookdirectory(Bookname,Line[0],Line[1],Correct);
                DirectoryList.add(Bookdt);
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
