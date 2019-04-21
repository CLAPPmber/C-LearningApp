package com.bignerdranch.android.CLearning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.CacheTool.ACache;
import com.HttpTool.API;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.google.gson.Gson;

import java.security.PrivateKey;

public class FeedBack extends AppCompatActivity {
    private EditText EditContext;
    private Button SubmitButtin;
    private ImageView BackButton;
    private ACache acache;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        acache = ACache.get(this);
        EditContext = (EditText)findViewById(R.id.feedback_content);
        SubmitButtin = (Button) findViewById(R.id.feedback_submit);
        BackButton = (ImageView)findViewById(R.id.feedback_back);
        BackButton.setOnClickListener(onclick);
        SubmitButtin.setOnClickListener(onclick);
        mContext = this;
    }
    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.feedback_back:
                    Back();
                    break;
                case R.id.feedback_submit:
                    Submit();
                    break;
            }
        }
    };

    public void Back(){
//        Intent intent = new Intent(FeedBack.this, HomePage.class);
//        startActivity(intent);
        this.finish();
    }

    public void Submit(){
        String account = acache.getAsString("Login");
        String content = EditContext.getText().toString();
        FeedBackContent fbc = new FeedBackContent(account,content);
        HttpUtil.sendOkHttpPostRequest(API.Url_Feed_Back,
                new Gson().toJson(fbc),
                new OnServerCallBack<com.HttpTool.FeedBack<String>,String>(){
            @Override
            public void onSuccess(String data) {
                Looper.prepare();
                Toast.makeText(mContext,"提交反馈成功",Toast.LENGTH_SHORT).show();
                finish();
                Looper.loop();
            }
            @Override
            public void onFailure(int code, String msg) {
                Looper.prepare();
                Toast.makeText(mContext,"反馈提交失败",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }

}


class FeedBackContent{
    private String account;
    private String context;
    FeedBackContent(String account,String content){
        this.account = account;
        this.context = content;
    }
}