package com.bignerdranch.android.CLearning;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Type.Chapter;

import java.util.List;

import static com.bignerdranch.android.CLearning.PracticeActivity.chapter_data;

/**
 * Created by Administrator on 2017\11\26 0026.
 */

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

  private List<Chapter> mChapters;
  private Context mContext ;
  static class ViewHolder extends RecyclerView.ViewHolder{
    TextView chaptername ;
    TextView quesnum;
    ImageView cpimg;
    public ViewHolder(View view){
      super(view);
      chaptername = (TextView) view.findViewById(R.id.cpname);
      quesnum = (TextView)view.findViewById(R.id.quesnum);
      cpimg = (ImageView)view.findViewById(R.id.cpimg);
    }
  }
  public ChapterAdapter(List<Chapter> chapterList){
    mChapters = chapterList;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if(mContext == null){
      mContext = parent.getContext();
    }
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.practice_item,parent,false);
    final ViewHolder holder = new ViewHolder(view);
    holder.cpimg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //章节图片点击事件
        int position = holder.getAdapterPosition();
        Chapter chapter = mChapters.get(position);

        chapter_data.set_now_chapter(chapter.get_chapter_num());//获取访问的是第几章
        Intent intent =new Intent(mContext,PracticingActivity.class);
        mContext.startActivity(intent);

      }
    });

    return holder;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Chapter chapter = mChapters.get(position);
    holder.chaptername.setText(chapter.getchaptername());
    holder.quesnum.setText("进度： "+(chapter_data.get_chapter_progress(chapter.get_chapter_num()))+"/"+chapter_data.get_chapter_max_num(chapter.get_chapter_num()));
  }

  @Override
  public int getItemCount() {
    return mChapters.size();
  }
}
