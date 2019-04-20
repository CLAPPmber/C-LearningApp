package com.bignerdranch.android.CLearning;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Create by Fushicho on 2019/1/24
 * 习题系统活动主界面
 */

import com.CacheTool.ACache;
import com.HttpTool.API;
import com.HttpTool.FeedBack;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.Type.Exer_test_msg;
import com.Type.Get_Chapter_Msg;
import com.Type.Get_Progress;
import com.Type.Input_user_flag;
import com.Type.Retprorec;
import com.google.gson.Gson;

import java.util.List;

import static java.lang.Integer.valueOf;

public class ExerciseSystem extends Fragment {

    private ACache acache;
    private String user;
    private View v;
    private Button button1;
    private Button button2;
//    private Button button3;
    private boolean isnew=false;
    static int flag;
    static Exer_test_msg test0;
    static Exer_test_msg test1;
    static boolean key0,key1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = inflater.inflate(R.layout.exercise_system_layout,container,false);

        key0=false;
        key1=false;

        //设置按钮图片的大小
        set_button_size();
        if(!isnew)
            set_test_adaper();  //试题目录信息更新


        return v;
    }

    //设置按钮图片的大小
    private void set_button_size(){
        //添加button1(课后习题),并设置图片的大小(无法在xml中设置图片大小,只能在此设置
        button1=(Button) v.findViewById(R.id.exercise_button_1);
        Drawable drawable1=getResources().getDrawable(R.drawable.exercise_book_pic);
        drawable1.setBounds(0,0,200,200);
        button1.setCompoundDrawables(drawable1,null,null,null);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                set_test_progress(0);   //用户做题进度更新
                if(key0) {
                    Intent intent = new Intent(getActivity(), Practice.class);
                    startActivity(intent);
                }
            }
        });
        //添加button2(试题训练),并设置图片的大小
        button2=(Button) v.findViewById(R.id.exercise_button_2);
        Drawable drawable2=getResources().getDrawable(R.drawable.exercise_test_pic);
        drawable2.setBounds(0,0,200,200);
        button2.setCompoundDrawables(drawable2,null,null,null);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                set_test_progress(1);   //用户做题进度更新
                if(key1) {
                    Intent intent = new Intent(getActivity(), Practice.class);
                    startActivity(intent);
                }
            }
        });
        //添加button3(社区答疑),并设置图片的大小
//        button3=(Button) v.findViewById(R.id.exercise_button_3);
//        Drawable drawable3=getResources().getDrawable(R.drawable.city_pic);
//        drawable3.setBounds(0,0,200,200);
//        button3.setCompoundDrawables(drawable3,null,null,null);
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), CitySystem.class);
//                startActivity(intent);
//            }
//        });
    }

    //联网获取课后习题和试卷章节信息
    private void set_test_adaper(){

        test0=new Exer_test_msg();
        test1=new Exer_test_msg();

        String URL=API.Url_Get_Chapter_Msg+"?flag=0";
        HttpUtil.sendOkHttpGetRequest(URL,new OnServerCallBack<FeedBack<List<Get_Chapter_Msg>>,List<Get_Chapter_Msg>>(){
            @Override
            public void onSuccess(List<Get_Chapter_Msg> data) {//操作成功
                 for(int i=0;i<data.size();i++){
                     test0.set_Exer_test_msg(valueOf(data.get(i).getTest_num()),data.get(i).getTest_name(),0,valueOf(data.get(i).getTest_total()));
                 }
                 test0.setN(data.size());

      //          acache.put(acache.getAsString("Login"),test0.toString()); //将进度存入缓存
            }
            @Override
            public void onFailure(int code, String msg) {//操作错误
                Log.e("err",msg);

            }
        });


       String URL1=API.Url_Get_Chapter_Msg+"?flag=1";
        HttpUtil.sendOkHttpGetRequest(URL1,new OnServerCallBack<FeedBack<List<Get_Chapter_Msg>>,List<Get_Chapter_Msg>>(){
            @Override
            public void onSuccess(List<Get_Chapter_Msg> data) {//操作成功
                for(int i=0;i<data.size();i++){
                    test1.set_Exer_test_msg(valueOf(data.get(i).getTest_num()),data.get(i).getTest_name(),0,valueOf(data.get(i).getTest_total()));
                }
                test1.setN(data.size());
                //          acache.put(acache.getAsString("Login"),test0.toString()); //将进度存入缓存
            }
            @Override
            public void onFailure(int code, String msg) {//操作错误
                Looper.prepare();
                Log.e("err",msg);
                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });

        isnew=true;
    }


    private void set_test_progress(int k){

        acache=ACache.get(getActivity());
        user=acache.getAsString("Login");
//        Input_user_flag input=new Input_user_flag(user,flag);

        if(k==0) {
            String URL = API.Url_Get_Progress + "?account=" + user + "&flag=" + 0;
            HttpUtil.sendOkHttpGetRequest(URL, new OnServerCallBack<FeedBack<List<Get_Progress>>, List<Get_Progress>>() {
                @Override
                public void onSuccess(List<Get_Progress> data) {//操作成功

                    for (int i = 0; i < data.size(); i++)
                        test0.setComplete(data.get(i).getTest_num(), data.get(i).getTest_complete_count());
                    key0 = true;

                }

                @Override
                public void onFailure(int code, String msg) {//操作错误
//                    int a;
                    Looper.prepare();
                    Log.e("err", msg);
                    Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            String URL1 = API.Url_Get_Progress + "?account=" + user + "&flag=" + 1;
            HttpUtil.sendOkHttpGetRequest(URL1, new OnServerCallBack<FeedBack<List<Get_Progress>>, List<Get_Progress>>() {
                @Override
                public void onSuccess(List<Get_Progress> data) {//操作成功
                    for (int i = 0; i < data.size(); i++)
                        test1.setComplete(data.get(i).getTest_num(), data.get(i).getTest_complete_count());
                    key1 = true;
                }

                @Override
                public void onFailure(int code, String msg) {//操作错误
//                    int a;
                    Log.e("err", msg);
                    Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

/*
    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {                       //为所有按钮创建点击事件
            switch (view.getId()) {
                case R.id.exercise_button_1:
                    Intent intent1 = new Intent(getActivity(), PracticeActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.exercise_button_2:
//                    Intent intent2 = new Intent(getActivity(),Test.class);
//                    startActivity(intent2);
                    break;

            }
        }
    };
    */
}
