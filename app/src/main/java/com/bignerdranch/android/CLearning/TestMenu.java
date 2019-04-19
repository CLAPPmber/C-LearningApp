package com.bignerdranch.android.CLearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import com.Type.TestChapter;

import java.util.List;

import static com.bignerdranch.android.CLearning.Practice.chapter_data;

/**
 *  Create by Fushicho on 2019/1/24
 *  用于编写试题训练界面的RecyclerView部分
 **/

public class TestMenu extends RecyclerView.Adapter<TestMenu.ViewHolder> {

    private List<TestChapter> mChapters;                                 //习题章节存放链表
    private Context mContext ;

    static class ViewHolder extends RecyclerView.ViewHolder{             //定义ViewHolder类继承RecyclerView的ViewHolder
        TextView  chapternum ;                                           //习题号
        TextView  chaptername ;                                          //习题名称
        TextView  progress;                                              //习题进度
        View      chapterview;                                           //章节视图
//        ImageView cpimg;                                                 //做题图片

        public ViewHolder(View view){
            super(view);
            chapterview=view;
            chapternum = (TextView) view.findViewById(R.id.cpname1);
            chaptername = (TextView) view.findViewById(R.id.cpname2);
            progress = (TextView)view.findViewById(R.id.progress);
//            cpimg = (ImageView)view.findViewById(R.id.cpimg);
        }
    }

    public TestMenu(List<TestChapter> chapterList){                     //构造函数
        mChapters = chapterList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {     //ViewHolder的OnCreate函数
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_menu_layout,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.chapterview.setOnClickListener(new View.OnClickListener() {           //为图片创建点击事件
            @Override
            public void onClick(View v) {
                //章节图片点击事件
                int position = holder.getAdapterPosition();
//                TestChapter tc=mChapters.get(position);
//                Toast.makeText(v.getContext(),"进入ing",Toast.LENGTH_SHORT).show();

                TestChapter chapter = mChapters.get(position);
                chapter_data.set_now_chapter(chapter.getTest_num());//获取访问的是第几章
                Intent intent =new Intent(mContext,Practicing.class);
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        TestChapter chapter = mChapters.get(position);
        holder.chapternum.setText(chapter.getTest_fir_name());
        holder.chaptername.setText(chapter.getTest_sec_name());
        holder.progress.setText(chapter.getProgress());
    }

    @Override
    public int getItemCount() {
        return mChapters.size();
    }


}
