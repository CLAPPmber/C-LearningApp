package com.bignerdranch.android.CLearning;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ImgTool.ImageUtils;
import com.Type.VedioCard;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2018\4\9 0009.
 */

public class vedio_card_adapter extends RecyclerView.Adapter<vedio_card_adapter.ViewHolder> {

    private Context mContext ;
    private List<VedioCard> mVedioCards;
    private Bitmap mBitmap;
    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView vedioimg;
        TextView Vname;
        public ViewHolder(View view){
            super(view);
            vedioimg = (ImageView) view.findViewById(R.id.vedio_card_image);
            Vname = (TextView) view.findViewById(R.id.vedio_card_name);
        }
    }

    public vedio_card_adapter(List<VedioCard> vedioCardList){
        mVedioCards = vedioCardList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vedio_card_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.vedioimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                VedioCard Vc = mVedioCards.get(position);
                Intent GotoVedioList = new Intent(mContext, VedioList.class);
                GotoVedioList.putExtra("vname",Vc.getVname());
                mContext.startActivity(GotoVedioList);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VedioCard vedioCard = mVedioCards.get(position);
        holder.Vname.setText(vedioCard.getVname());
        ImageUtils.setImageBitmap(vedioCard.getImg(),holder.vedioimg,String.valueOf(position),mContext);
//        GetImg getImg = new GetImg(vedioCard.getImg());
//        getImg.start();
//        try{
//            getImg.join();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        holder.vedioimg.setImageBitmap(mBitmap);
    }

    @Override
    public int getItemCount() {
        return mVedioCards.size();
    }

    class GetImg extends Thread{
        String ImgUrl;
        GetImg(String url){
            ImgUrl = url;
        }
        @Override
        public void run() {
               mBitmap = Getimg(ImgUrl);
           }
    }

    //通过网络获取图片
    public Bitmap Getimg(String url){
        URL myFileUrl;
        Bitmap bitmap = null  ;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    //保存文件到本地




}

