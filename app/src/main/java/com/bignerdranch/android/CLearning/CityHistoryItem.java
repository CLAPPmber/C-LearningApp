package com.bignerdranch.android.CLearning;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Type.City_history_item;

import java.util.Date;
import java.util.List;

public class CityHistoryItem extends RecyclerView.Adapter<CityHistoryItem.ViewHolder> {

    private List<City_history_item> mCityHistoryItem;                   //社区动态存放链表
    private Context mContext ;

    static class ViewHolder extends RecyclerView.ViewHolder{             //定义ViewHolder类继承RecyclerView的ViewHolder
        View itemView;                                                   //整条动态任意部分
        TextView author_message ;                                       //作者发布的消息内容
        ImageView post_pic;                                              //该动态发布附带的图片
        TextView  post_date;                                             //该动态发布日期

        public ViewHolder(View view){
            super(view);
            itemView=view;
            author_message=(TextView)view.findViewById(R.id.city_history_item_user_message);
            post_pic=(ImageView) view.findViewById(R.id.city_history_item_pic);
            post_date=(TextView)view.findViewById(R.id.city_history_item_time);
        }
    }

    public CityHistoryItem(List<City_history_item> city_history_item_List){                     //构造函数
        mCityHistoryItem = city_history_item_List;
    }

    @Override
    public CityHistoryItem.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {     //ViewHolder的OnCreate函数
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_history,parent,false);
        final CityHistoryItem.ViewHolder holder = new CityHistoryItem.ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {           //为图片创建点击事件
            @Override
            public void onClick(View v) {
                //章节图片点击事件
                int position = holder.getAdapterPosition();
//                TestChapter tc=mChapters.get(position);
//                Toast.makeText(v.getContext(),"进入ing",Toast.LENGTH_SHORT).show();

                City_history_item chapter = mCityHistoryItem.get(position);
//                chapter_data.set_now_chapter(chapter.getTest_num());//获取访问的是第几章
//                Intent intent =new Intent(mContext,CityDetails.class);
//                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        City_history_item chapter = mCityHistoryItem.get(position);
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
        return mCityHistoryItem.size();
    }

}