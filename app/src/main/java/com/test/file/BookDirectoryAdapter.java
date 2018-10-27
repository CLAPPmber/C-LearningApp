package com.test.file;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Type.Bookdirectory;
import com.bignerdranch.android.CLearning.R;

import java.util.List;

/**
 * Created by Administrator on 2018\3\18 0018.
 */

public class BookDirectoryAdapter extends RecyclerView.Adapter<BookDirectoryAdapter.ViewHolder> {

    private List<Bookdirectory> BookDirectory;
    private Context mContext ;
    private boolean IsDetail;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView DirectoryName;
        LinearLayout BookItem;
        public ViewHolder(View view){
            super(view);
            BookItem = (LinearLayout) view.findViewById(R.id.book_item);
            DirectoryName = (TextView) view.findViewById(R.id.book_directory_item);
        }
    }
    public BookDirectoryAdapter(List<Bookdirectory> bookDirectory,boolean isdetail){
        BookDirectory = bookDirectory;
        IsDetail = isdetail;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_directory_tiem,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.BookItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Bookdirectory Bd = BookDirectory.get(position);
                if(Bd.getBookName().split("\\.")[1].equals("PDF")||Bd.getBookName().split("\\.")[1].equals("pdf")){
                    if(!IsDetail){
                        Intent GotoBookDetail = new Intent(mContext,PdfBook.class);
                        GotoBookDetail.putExtra("bookname",Bd.getBookName());
                        GotoBookDetail.putExtra("page",Bd.getPage());
                        GotoBookDetail.putExtra("correct",Bd.getCorrect());
                        mContext.startActivity(GotoBookDetail);
                        return;
                    }
                    PdfBook.dispalypdfview("Book/"+Bd.getBookName().split("\\.")[0]+"/"+Bd.getBookName(),Integer.parseInt(Bd.getPage()),Bd.getCorrect());
                }else{
                    Intent GotoBookDetail = new Intent(mContext,BookDetail.class);
                    GotoBookDetail.putExtra("bookname",Bd.getBookName());
                    GotoBookDetail.putExtra("directoryname",Bd.getDirectoryName());
                    mContext.startActivity(GotoBookDetail);
                }

            }
        });

        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder ,int position){
        Bookdirectory Bd = BookDirectory.get(position);
        holder.DirectoryName.setText(Bd.getDirectoryName());
    }
    @Override
    public int getItemCount(){
        return BookDirectory.size();
    }
}
