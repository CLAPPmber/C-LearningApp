package com.bignerdranch.android.CLearning;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.Type.Book;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends Fragment {

    private Book[] books = { new Book("C语言程序设计.PDF",R.drawable.book_cprogramming,19),
                             new Book("C++入门.txt",R.drawable.book_primer_c11,0),
                             new Book("C++入门2.pdf",R.drawable.book_primer2_c11,0),
                             new Book("我的第一本C++书.pdf",R.drawable.book_myfirst_c11,12),
                             new Book("C++99个常见错误.pdf",R.drawable.book_99e,19),
                             new Book("C++编程思想.pdf",R.drawable.book_think_in_c11,18),
                             new Book("C++沉思录.pdf",R.drawable.book_ruminations_on_c11,27),
                             new Book("Effective C++.pdf",R.drawable.book_effective_c11,29),
                             new Book("21St Century C.PDF",R.drawable.book_21st_century_c11,21)};

    public BookActivity() {
        // Required empty public constructor
    }

    private List<Book> mBookList = new ArrayList<>();

    private BookAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_book,container,false);
        super.onCreate(savedInstanceState);
        iniBooks();
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_book);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new BookAdapter(mBookList);
        recyclerView.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBookList.clear();
    }

    public void iniBooks(){
        for(int i=0;i<books.length;i++){
            mBookList.add(books[i]);
        }
    }
}
