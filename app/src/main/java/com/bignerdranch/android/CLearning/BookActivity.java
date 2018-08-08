package com.bignerdranch.android.CLearning;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.Type.Book;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private Book[] books = { new Book("C语言程序设计.PDF",R.drawable.book_cprogramming,19),
                             new Book("C++入门.txt",R.drawable.book_primer_c11,0),
                             new Book("C++入门2.pdf",R.drawable.book_primer2_c11,0),
                             new Book("我的第一本C++书.pdf",R.drawable.book_myfirst_c11,12),
                             new Book("C++99个常见错误.pdf",R.drawable.book_99e,19),
                             new Book("C++编程思想.pdf",R.drawable.book_think_in_c11,18),
                             new Book("C++沉思录.pdf",R.drawable.book_ruminations_on_c11,27),
                             new Book("Effective C++.pdf",R.drawable.book_effective_c11,29),
                             new Book("21St Century C.PDF",R.drawable.book_21st_century_c11,21)};



    private List<Book> mBookList = new ArrayList<>();

    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarbook);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
        }
        iniBooks();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_book);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new BookAdapter(mBookList);
        recyclerView.setAdapter(mAdapter);
    }

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

    public void iniBooks(){
        for(int i=0;i<books.length;i++){
            mBookList.add(books[i]);
        }
    }
}
