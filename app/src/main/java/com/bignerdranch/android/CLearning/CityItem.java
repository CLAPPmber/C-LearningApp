package com.bignerdranch.android.CLearning;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Type.City_item;

import java.util.Date;
import java.util.List;

/**
 * Create by Fushicho on 2019/1/29
 * 用于编写社区动态界面的RecyclerView评论部分
 */

public class CityItem extends RecyclerView.Adapter<CityItem.ViewHolder> {

    private List<City_item> mCityItem;                                  //社区动态存放链表
    private Context mContext ;

    static class ViewHolder extends RecyclerView.ViewHolder{             //定义ViewHolder类继承RecyclerView的ViewHolder
        View itemView;                                                  //整条动态任意部分
        ImageView author_pic;                                           //作者头像
        TextView author_name ;                                           //作者名称
        TextView  author_message ;                                       //作者发布的消息内容
        ImageView post_pic;                                              //该动态发布附带的图片
        TextView  post_date;                                             //该动态发布日期

        public ViewHolder(View view){
            super(view);
            itemView=view;
            author_pic=(ImageView) view.findViewById(R.id.city_item_user_pic);
            author_name=(TextView)view.findViewById(R.id.city_item_user_name);
            author_message=(TextView)view.findViewById(R.id.city_item_user_message);
            post_pic=(ImageView) view.findViewById(R.id.city_item_pic);
            post_date=(TextView)view.findViewById(R.id.city_item_time);
        }
    }

    public CityItem(List<City_item> city_item_List){                     //构造函数
        mCityItem = city_item_List;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {     //ViewHolder的OnCreate函数
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {           //为图片创建点击事件
            @Override
            public void onClick(View v) {
                //章节图片点击事件
                int position = holder.getAdapterPosition();
//                TestChapter tc=mChapters.get(position);
//                Toast.makeText(v.getContext(),"进入ing",Toast.LENGTH_SHORT).show();

                City_item chapter = mCityItem.get(position);
//                chapter_data.set_now_chapter(chapter.getTest_num());//获取访问的是第几章
                Intent intent =new Intent(mContext,CityDetails.class);
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        City_item chapter = mCityItem.get(position);
        holder.author_name.setText(chapter.getAuthor_name());
        holder.author_message.setText(chapter.getMessage());
        holder.post_date.setText(get_date(chapter.getDate()));
    }

    private String get_date(Date date){    //将Date格式的数据转为String格式,输出格式eg:2019年01月05日  06:14
        String str="";
        str+=date.getYear();
        str+="年";
        if(date.getMonth()<10)
            str+="0";
        str+=date.getMonth();
        str+="月";
        if(date.getDay()<10)
            str+="0";
        str+=date.getDay();
        str+="日  ";
        if(date.getHours()<10)
            str+="0";
        str+=date.getHours();
        str+=":";
        if(date.getMinutes()<10)
            str+="0";
        str+=date.getMinutes();
        return str;
    }

    @Override
    public int getItemCount() {
        return mCityItem.size();
    }

}