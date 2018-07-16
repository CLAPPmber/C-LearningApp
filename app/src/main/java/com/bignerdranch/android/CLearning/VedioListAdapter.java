package com.bignerdranch.android.CLearning;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Type.Vedio;

import java.util.List;

/**
 * Created by Administrator on 2018\4\9 0009.
 */

public class VedioListAdapter extends RecyclerView.Adapter<VedioListAdapter.ViewHolder>{

    private Context mContext ;
    private List<Vedio> mVedioList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout ListItem;
        TextView ListItemName;
        public ViewHolder(View view){
            super(view);
            ListItem = (LinearLayout)view.findViewById(R.id.vedio_list_item);
            ListItemName = (TextView) view.findViewById(R.id.vedio_list_item_name);
        }
    }

    public VedioListAdapter(List<Vedio> vedioCardList){
        mVedioList = vedioCardList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vedio_list_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Vedio vedio = mVedioList.get(position);
                String video =  vedio.getUrl();
                Intent openVideo = new Intent(Intent.ACTION_VIEW);
                openVideo.setDataAndType(Uri.parse(video), "video/*");
                mContext.startActivity(openVideo);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Vedio vedio = mVedioList.get(position);
        holder.ListItemName.setText(vedio.getVedioName());
    }

    @Override
    public int getItemCount() {
        return mVedioList.size();
    }
}
