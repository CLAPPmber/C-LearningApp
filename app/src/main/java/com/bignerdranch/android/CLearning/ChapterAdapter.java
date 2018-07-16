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
                int position = holder.getAdapterPosition();
                Chapter  chapter = mChapters.get(position);
                Toast.makeText(view.getContext(),"into practice number"+chapter.getQuestionnum(),Toast.LENGTH_SHORT)
                        .show();
                String video = "http://123.207.25.239:2332/043%E7%AC%AC%E5%85%AB%E7%AB%A0%20%E6%8C%87%E9%92%8803(%E6%96%B0%E7%89%88).mp4";
                Intent openVideo = new Intent(Intent.ACTION_VIEW);
                openVideo.setDataAndType(Uri.parse(video), "video/*");
                mContext.startActivity(openVideo);
//                String url = "http://study.163.com/course/courseLearn.htm?courseId=271005#/learn/video?lessonId=381169&courseId=271005";//示例，实际填你的网络视频链接
//                String extension = MimeTypeMap.getFileExtensionFromUrl(url);
//                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//                Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
//                mediaIntent.setDataAndType(Uri.parse(url), mimeType);
//                mContext.startActivity(mediaIntent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chapter chapter = mChapters.get(position);
        holder.chaptername.setText(chapter.getchaptername());
        holder.quesnum.setText("试题数量： "+chapter.getQuestionnum());
    }

    @Override
    public int getItemCount() {
        return mChapters.size();
    }
}
