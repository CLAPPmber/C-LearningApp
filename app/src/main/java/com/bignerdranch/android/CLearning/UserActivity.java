package com.bignerdranch.android.CLearning;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.CacheTool.ACache;
import com.HttpTool.API;
import com.HttpTool.FeedBack;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.HttpTool.User;
import com.ImgTool.RoundAngleImg;
import com.Type.Retprorec;
import com.google.gson.Gson;

import java.security.PublicKey;
import java.util.List;

public class UserActivity extends Fragment {
    private ACache acache;//缓存框架
    private RoundAngleImg userHeadImg;
    private TextView modification_userImg;
    private Button button_modification;
    private Button button_quit;
    private Context mContext;
    private TextView userAccount;
    private TextView practiceProgress;
    private Integer dataSize = 0;
    public UserActivity(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext();
        acache=ACache.get(mContext);//创建ACache组件
        View v = inflater.inflate(R.layout.activity_user,container,false);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.img2);
        userHeadImg = v.findViewById(R.id.user_head_img);
        practiceProgress = v.findViewById(R.id.practice_progress);

        modification_userImg = v.findViewById(R.id.modification_userImg);
        modification_userImg.getPaint().setAntiAlias(true);
        userAccount = v.findViewById(R.id.user_account);
        userHeadImg.setBitmap(bitmap);
        userHeadImg.setmOuterRing(0);
        userHeadImg.setColor(Color.WHITE);
        userHeadImg.setOuterRingAlpha(0);

        button_modification = v.findViewById(R.id.modification_button);
        button_quit = v.findViewById(R.id.quit_button);
        button_modification.setOnClickListener(onclick);
        button_quit.setOnClickListener(onclick);

        userAccount.setText(acache.getAsString("Login"));

        getPracticeprogress();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        practiceProgress.setText(dataSize+"/65");
    }

    View.OnClickListener onclick = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.modification_button:
                        break;
                    case R.id.quit_button:
                        showNormalDialog();
                        break;
                }
            }
    };

    /**
     *获取已做记录，返回每一个章节的进度
     */
    public void getPracticeprogress(){

        User user = new User(acache.getAsString("Login"));
        dataSize = 0;
        HttpUtil.sendOkHttpPostRequest(API.Url_GetAllRec,new Gson().toJson(user),new OnServerCallBack<FeedBack<List<Retprorec>>,List<Retprorec>>(){
            @Override
            public void onSuccess(List<Retprorec> data) {//操作成功
                for(int i = 0 ;i<data.size();i++){
                    dataSize += data.get(i).chapter_rec;
                }
                restart();
            }
            @Override
            public void onFailure(int code, String msg) {
                Looper.prepare();
                Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
    }

    private void restart(){
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onStart();
            }
        });
    }




    /**
     *对话框
     */
    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =new AlertDialog.Builder(this.getContext());
        normalDialog.setTitle("提示");
        normalDialog.setMessage("确定退出吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        acache.remove("Login");
                        Intent intent=new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
}
