package com.bignerdranch.android.CLearning;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Type.Book;
import com.bumptech.glide.Glide;
import com.test.file.BookDirectory;
import java.util.List;

/**
 * Created by Administrator on 2017\11\27 0027.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context mContext ;
    private List<Book> mBooks;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        ImageView bookImg;
        TextView bookName;
        public ViewHolder(View view){
            super(view);
            bookImg = (ImageView)view.findViewById(R.id.book_image);
            bookName = (TextView) view.findViewById(R.id.book_name);
        }
    }
    public BookAdapter(List<Book> books){
        mBooks = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.book_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.bookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Book book = mBooks.get(position);
                Intent GotoBookDirectory = new Intent(mContext, BookDirectory.class);
                GotoBookDirectory.putExtra("bookname",book.getBookName());
                GotoBookDirectory.putExtra("correct",book.getCorrect());
                mContext.startActivity(GotoBookDirectory);
            }
        });
        return  holder;
}

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = mBooks.get(position);
        holder.bookName.setText(book.getBookName().split("\\.")[0]);
        Glide.with(mContext).load(book.getImgId()).into(holder.bookImg);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }
}
