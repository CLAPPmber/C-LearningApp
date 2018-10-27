package com.test.file;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.bignerdranch.android.CLearning.R;
import com.github.barteksc.pdfviewer.PDFView;

public class PdfBook extends AppCompatActivity {
    private static DrawerLayout mDrawerLayout;
    private String BookName;
    private String Page;
    private String AbsPath = "Book/";
    private static PDFView pdfview;
    private TextView BookPdfName;
    private static int Correct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_pdf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarbookpdf);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
            actionBar.setDisplayShowTitleEnabled(false);
        }

//引入侧边滑动目录
        mDrawerLayout = (DrawerLayout) findViewById(R.id.pdfbook_drawer);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.bookpdf_directory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        BookDirectoryAdapter Bdadapter = new BookDirectoryAdapter(BookDirectory.DirectoryList,true);
        recyclerView.setAdapter(Bdadapter);
//
        pdfview = (PDFView) findViewById(R.id.pdfView);
        Intent getintent = getIntent();
        BookName = getintent.getStringExtra("bookname");
        Correct = getintent.getIntExtra("correct",0);
        BookPdfName = (TextView)findViewById(R.id.bookpdf_name);
        BookPdfName.setText(BookName.split("\\.")[0]);
        Page = getintent.getStringExtra("page");
        dispalypdfview(AbsPath+BookName.split("\\.")[0]+"/"+BookName,Integer.parseInt(Page),Correct);
    }

    /*
     *获取pdf资源并展示
     */
    public static void  dispalypdfview(String assetspath,int page,int correct){
        pdfview.fromAsset(assetspath)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(page+correct)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true)
                .spacing(0)
                .load();
        mDrawerLayout.closeDrawers();
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
