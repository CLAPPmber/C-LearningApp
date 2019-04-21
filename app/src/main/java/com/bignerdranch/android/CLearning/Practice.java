package com.bignerdranch.android.CLearning;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.CacheTool.ACache;
import com.HttpTool.API;
import com.HttpTool.FeedBack;
import com.HttpTool.HttpUtil;
import com.HttpTool.OnServerCallBack;
import com.HttpTool.User;
import com.Type.Chapter_data;
import com.Type.Get_Progress;
import com.Type.Retprorec;
import com.Type.TestChapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Database.DBUtil;

import static com.bignerdranch.android.CLearning.ExerciseSystem.flag;
import static com.bignerdranch.android.CLearning.ExerciseSystem.test0;
import static com.bignerdranch.android.CLearning.ExerciseSystem.test1;
import static java.lang.Integer.valueOf;

/**
 * Create by Fushicho on 2019/1/24
 * 习题系统 - 课后习题界面
 */

public class Practice extends AppCompatActivity {

    private List<TestChapter> mChapterList= new ArrayList<>(); ;         //定义试题章节链表
    static  Chapter_data chapter_data=new Chapter_data();
    private ACache acache;
    static  String user;
    private TextView tv;
    static Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_layout);

        //创建返回行
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_practice);
        tv=(TextView)findViewById(R.id.test_title);
        if(flag==0)
            tv.setText("课后习题");
        else
            tv.setText("试题训练");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
            actionBar.setDisplayShowTitleEnabled(false);
        }


        //创建ACache组件并获取用户名
        acache=ACache.get(Practice.this);
        user=acache.getAsString("Login");

        set_test_progress(flag);               //联网获取用户做题记录
        //get_user_progress();                 //获取用户做题纪录（连网）
        //importChapter();                     //添加章节信息
//        update_Chapter_msg();                  //更新目录信息

//        updata_adapter();                    //更新导入章节目录


        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 2) { //如果消息是刚才发送的标识
                    update_Chapter_msg();                  //更新目录信息
                    updata_adapter();                    //更新导入章节目录
                }
            };
        };
    }

    private void set_test_progress(int k){ //联网获取用户做题记录
        if(k==0) {
            String URL = API.Url_Get_Progress + "?account=" + user + "&flag=" + 0;
            HttpUtil.sendOkHttpGetRequest(URL, new OnServerCallBack<FeedBack<List<Get_Progress>>, List<Get_Progress>>() {
                @Override
                public void onSuccess(List<Get_Progress> data) {//操作成功
                    for (int i = 0; i < data.size(); i++)
                        test0.setComplete(data.get(i).getTest_num(), data.get(i).getTest_complete_count());
                    handler.sendEmptyMessage(2); //工作线程的handler发送消息
                }
                @Override
                public void onFailure(int code, String msg) {//操作错误
//                    int a;
                    Log.e("err", msg);
                    Toast.makeText(Practice.this, "网络异常", Toast.LENGTH_SHORT).show();
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
                    handler.sendEmptyMessage(2); //工作线程的handler发送消息
                }
                @Override
                public void onFailure(int code, String msg) {//操作错误
                    Log.e("err", msg);
                    Toast.makeText(Practice.this, "网络异常", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    // //更新目录信息
    private void update_Chapter_msg(){
        if(flag==0){
            for(int i=1;i<= test0.getN();i++){
                chapter_data.set_chapter_progress(i,test0.getComplete(i));
                chapter_data.set_chapter_max_num(i,test0.getTotal(i));
//                TestChapter chapter = new TestChapter(i,"第"+i+"章",test0.getName(i) ,chapter_data.get_chapter_progress(i) +"/"+ chapter_data.get_chapter_max_num(i), i*111);
//                mChapterList.add(chapter);
            }
        }
        else{
            for(int i=1;i<= test1.getN();i++){
                chapter_data.set_chapter_progress(i,test1.getComplete(i));
                chapter_data.set_chapter_max_num(i,test1.getTotal(i));
//                TestChapter chapter = new TestChapter(i,"第"+i+"章",test1.getName(i) ,chapter_data.get_chapter_progress(i) +"/"+ chapter_data.get_chapter_max_num(i), i*111);
//                mChapterList.add(chapter);
            }
        }
    }

    //获取用户做题纪录（连网）
    private void get_user_progress(){
        User use = new User(user,"123456777"); //查询的用户账号,密码随意
        HttpUtil.sendOkHttpPostRequest(API.Url_GetAllRec,new Gson().toJson(use),new OnServerCallBack<FeedBack<List<Retprorec>>,List<Retprorec>>(){
            @Override
            public void onSuccess(List<Retprorec> data) {//操作成功
                for (int i = 1; i <= 11; i++)   //初始化数据
                    chapter_data.set_chapter_progress(i,0);
                if (data == null){
                    //为空不用操作
                }
                else {//若是成功获取数据就修改该用户的做题进度
                    for (int i = 0; i < data.size(); i++)  //更新不同的数据
                        chapter_data.set_chapter_progress(valueOf(data.get(i).chapter_num), valueOf(data.get(i).chapter_rec));
                }
            }
            @Override
            public void onFailure(int code, String msg) {
                //操作错误
                Log.e("err",msg);

            }
        });


    }



    //定义添加章节信息
    private void importChapter(){
        for(int i=1 ;i<=11;i++) {
            SQLiteDatabase myDateBase = DBUtil.openDatabase(Practice.this);
            String sql = "SELECT * FROM chapter WHERE chapter_num ="+i;
            String chapter_name="";
            try{
                Cursor c = myDateBase.rawQuery(sql,null);
                if (  c.moveToFirst()){
                    do{
                        chapter_name = c.getString(c.getColumnIndex("chapter_name"));

                    }while (c.moveToNext());
                }
                if (!c.isClosed()){
                    c.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            int num=0;
            sql = "SELECT * FROM question WHERE chapter_num ="+i;
            try{
                Cursor c = myDateBase.rawQuery(sql,null);
                if (  c.moveToFirst()){
                    do{
                        num++;

                    }while (c.moveToNext());
                }
                if (!c.isClosed()){
                    c.close();
                }
                if(myDateBase.isOpen()){
                    myDateBase.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            chapter_data.set_chapter_max_num(i,num);
            TestChapter chapter;
            if(flag==0)
                 chapter = new TestChapter(i,"第"+i+"章",chapter_name ,chapter_data.get_chapter_progress(i) +"/"+ num, i*111);
            else
                 chapter = new TestChapter(i,"试题"+i,chapter_name ,chapter_data.get_chapter_progress(i) +"/"+ num, i*111);
            mChapterList.add(chapter);
        }


    }

    @Override
    public void onStart(){  //调回时候刷新习题进度
        super.onStart();
        updata_adapter();
    }

    //更新章节信息
    private void  updata_adapter(){
        mChapterList.clear();
        if(flag==0){
            for(int i=1;i<= test0.getN();i++){
                TestChapter chapter;
                if(flag==0)
                    chapter = new TestChapter(i,"第"+i+"章",test0.getName(i) ,chapter_data.get_chapter_progress(i) +"/"+ chapter_data.get_chapter_max_num(i), i*111);
                else
                    chapter = new TestChapter(i,"试题"+i,test0.getName(i) ,chapter_data.get_chapter_progress(i) +"/"+ chapter_data.get_chapter_max_num(i), i*111);
               mChapterList.add(chapter);
            }
        }
        else{
            for(int i=1;i<= test1.getN();i++){
                TestChapter chapter;
                if(flag==0)
                    chapter = new TestChapter(i,"第"+i+"章",test1.getName(i) ,chapter_data.get_chapter_progress(i) +"/"+ chapter_data.get_chapter_max_num(i), i*111);
                else
                    chapter = new TestChapter(i,"试题"+i,test1.getName(i) ,chapter_data.get_chapter_progress(i) +"/"+ chapter_data.get_chapter_max_num(i), i*111);
                mChapterList.add(chapter);
            }
        }
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.recycler_view2);
        LinearLayoutManager layoutManager=new LinearLayoutManager(Practice.this);
        recyclerView.setLayoutManager(layoutManager);
        TestMenu adapter =new TestMenu(mChapterList);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onDestroy() {                                          //重写活动销毁前调用的函数
        super.onDestroy();
        mChapterList.clear();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
